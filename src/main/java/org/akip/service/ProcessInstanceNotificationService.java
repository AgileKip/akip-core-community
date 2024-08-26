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

    public ProcessInstanceNotificationService(
        ProcessInstanceNotificationRepository processInstanceNotificationRepository,
        ProcessInstanceNotificationMapper processInstanceNotificationMapper
    ) {
        this.processInstanceNotificationRepository = processInstanceNotificationRepository;
        this.processInstanceNotificationMapper = processInstanceNotificationMapper;
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
    public void save(Long eventId, ProcessInstance processInstance, ProcessInstanceEventType notificationType, String subscriberId) {
        AbstractNotificationService notificationService;

        switch (notificationType) {
            case TASK_COMPLETED:
                notificationService = new TaskCompletedNotificationService();
                break;
            case NOTE_ADDED:
                notificationService = new NoteAddedNotificationService();
                break;
            case NOTE_CHANGED:
                notificationService = new NoteChangedNotificationService();
                break;
            case NOTE_REMOVED:
                notificationService = new NoteRemovedNotificationService();
                break;
            case ATTACHMENT_ADDED:
                notificationService = new AttachmentAddedNotificationService();
                break;
            case ATTACHMENT_CHANGED:
                notificationService = new AttachmentChangedNotificationService();
                break;
            case ATTACHMENT_REMOVED:
                notificationService = new AttachmentRemovedNotificationService();
                break;
            default:
                throw new IllegalArgumentException("Unhandled notification type: " + notificationType);
        }

        ProcessInstanceNotification processInstanceNotification = notificationService.createNotification(eventId, processInstance);
        processInstanceNotification.setCreateDate(LocalDateTime.now());
        processInstanceNotification.setStatus(ProcessInstanceNotificationStatus.UNREAD);
        processInstanceNotification.setEventType(notificationType);
        processInstanceNotification.setSubscriberId(subscriberId);
        processInstanceNotification.setProcessInstance(processInstance);
        processInstanceNotification = processInstanceNotificationRepository.save(processInstanceNotification);
        processInstanceNotificationMapper.toDto(processInstanceNotification);
    }

    /**
     * Partially update a processInstanceNotification.
     *
     * @param processInstanceNotificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessInstanceNotificationDTO> partialUpdate(ProcessInstanceNotificationDTO processInstanceNotificationDTO) {
        log.debug("Request to partially update ProcessInstanceNotification : {}", processInstanceNotificationDTO);

        return processInstanceNotificationRepository
            .findById(processInstanceNotificationDTO.getId())
            .map(
                existingProcessInstanceNotification -> {
                    processInstanceNotificationMapper.partialUpdate(existingProcessInstanceNotification, processInstanceNotificationDTO);
                    return existingProcessInstanceNotification;
                }
            )
            .map(processInstanceNotificationRepository::save)
            .map(processInstanceNotificationMapper::toDto);
    }

    /**
     * Get all the processInstanceNotifications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceNotificationDTO> findAll() {
        log.debug("Request to get all ProcessInstanceNotifications");
        return processInstanceNotificationRepository
            .findAll()
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
