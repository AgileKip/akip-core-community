package org.akip.service;

import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.ProcessInstanceNotificationDTO;
import org.akip.service.mapper.ProcessInstanceNotificationMapper;
import org.akip.domain.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessInstanceNotification}.
 */
@Service
@Transactional
public class ProcessInstanceNotificationService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceNotificationService.class);

    private final ProcessInstanceNotificationRepository processInstanceNotificationRepository;

    private final ProcessInstanceNotificationMapper processInstanceNotificationMapper;

    private final TaskCompletedNotificationService taskCompletedNotificationService;

    private final NoteAddedNotificationService noteAddedNotificationService;

    private final NoteChangedNotificationService noteChangedNotificationService;

    private final NoteRemovedNotificationService noteRemovedNotificationService;

    private final AttachmentAddedNotificationService attachmentAddedNotificationService;

    private final AttachmentChangedNotificationService attachmentChangedNotificationService;

    private final AttachmentRemovedNotificationService attachmentRemovedNotificationService;






    @Autowired
    public ProcessInstanceNotificationService(
            ProcessInstanceNotificationRepository processInstanceNotificationRepository,
            ProcessInstanceNotificationMapper processInstanceNotificationMapper,
            @Lazy TaskCompletedNotificationService taskCompletedNotificationService,
            @Lazy NoteAddedNotificationService noteAddedNotificationService,
            @Lazy NoteChangedNotificationService noteChangedNotificationService,
            @Lazy NoteRemovedNotificationService noteRemovedNotificationService,
            @Lazy AttachmentAddedNotificationService attachmentAddedNotificationService,
            @Lazy AttachmentChangedNotificationService attachmentChangedNotificationService,
            @Lazy AttachmentRemovedNotificationService attachmentRemovedNotificationService
    ) {
        this.processInstanceNotificationRepository = processInstanceNotificationRepository;
        this.processInstanceNotificationMapper = processInstanceNotificationMapper;
        this.taskCompletedNotificationService = taskCompletedNotificationService;
        this.noteAddedNotificationService = noteAddedNotificationService;
        this.noteChangedNotificationService = noteChangedNotificationService;
        this.noteRemovedNotificationService = noteRemovedNotificationService;
        this.attachmentAddedNotificationService = attachmentAddedNotificationService;
        this.attachmentChangedNotificationService = attachmentChangedNotificationService;
        this.attachmentRemovedNotificationService = attachmentRemovedNotificationService;
    }

    /**
     * Save a processInstanceNotification.
     *
     * @param processInstanceNotificationDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstanceNotificationDTO save(ProcessInstanceNotificationDTO processInstanceNotificationDTO) {
        log.debug("Request to save ProcessInstanceNotification : {}", processInstanceNotificationDTO);
        ProcessInstanceNotification processInstanceNotification = processInstanceNotificationMapper.toEntity(
            processInstanceNotificationDTO
        );
        processInstanceNotification = processInstanceNotificationRepository.save(processInstanceNotification);
        return processInstanceNotificationMapper.toDto(processInstanceNotification);
    }

    /**
     * Save a processInstanceNotification.
     *
     * @param processInstance the entity to save.
     */
    public void save(Object source, ProcessInstance processInstance, ProcessInstanceEventType notificationType, String subscriberId) {
        AbstractNotificationService service = getNotificationService(notificationType);
        ProcessInstanceNotification processInstanceNotification = service.createNotification(source, processInstance);
        processInstanceNotification.setCreateDate(LocalDateTime.now());
        processInstanceNotification.setStatus(ProcessInstanceNotificationStatus.UNREAD);
        processInstanceNotification.setEventType(notificationType);
        processInstanceNotification.setSubscriberId(subscriberId);
        processInstanceNotification.setProcessInstance(processInstance);
        processInstanceNotification = processInstanceNotificationRepository.save(processInstanceNotification);
        processInstanceNotificationMapper.toDto(processInstanceNotification);
    }

    private AbstractNotificationService getNotificationService(ProcessInstanceEventType notificationType) {
        switch (notificationType) {
            case TASK_COMPLETED:
                return taskCompletedNotificationService;
            case NOTE_ADDED:
                return noteAddedNotificationService;
            case NOTE_CHANGED:
                return noteChangedNotificationService;
            case NOTE_REMOVED:
                return noteRemovedNotificationService;
            case ATTACHMENT_ADDED:
                return attachmentAddedNotificationService;
            case ATTACHMENT_CHANGED:
                return attachmentChangedNotificationService;
            case ATTACHMENT_REMOVED:
                return attachmentRemovedNotificationService;
            default:
                throw new IllegalArgumentException("Unhandled notification type: " + notificationType);
        }
    }

    /**
     * Get all the processInstanceNotifications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceNotificationDTO> findTop6BySubscriberIdOrderByIdDesc() {
        log.debug("Request to get all ProcessInstanceNotifications SubscriberId");
        return processInstanceNotificationRepository
            .findTop6BySubscriberIdOrderByIdDesc(SecurityUtils.getCurrentUserLogin().get())
            .stream()
            .map(processInstanceNotificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the processInstanceNotifications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Long countBySubscriberIdAndStatus() {
        log.debug("Request to get all ProcessInstanceNotificationsUnread SubscriberId");
        return processInstanceNotificationRepository
                .countBySubscriberIdAndStatus(SecurityUtils.getCurrentUserLogin().get(), ProcessInstanceNotificationStatus.UNREAD);
    }

    /**
     * Get one processInstanceNotification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceNotificationDTO> findOne(Long id) {
        log.debug("Request to get ProcessInstanceNotification : {}", id);
        return processInstanceNotificationRepository.findById(id).map(processInstanceNotificationMapper::toDto);
    }

    /**
     * Get one processInstanceNotification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public ProcessInstanceNotificationDTO readNotification(Long id) {
        log.debug("Request to get ProcessInstanceNotification : {}", id);
        ProcessInstanceNotification processInstanceNotification = processInstanceNotificationRepository.findById(id).get();
        processInstanceNotification.setStatus(ProcessInstanceNotificationStatus.READ);
        processInstanceNotification.setReadDate(LocalDateTime.now());
        return processInstanceNotificationMapper.toDto(processInstanceNotificationRepository.save(processInstanceNotification));
    }

    /**
     * Delete the processInstanceNotification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessInstanceNotification : {}", id);
        processInstanceNotificationRepository.deleteById(id);
    }
}
