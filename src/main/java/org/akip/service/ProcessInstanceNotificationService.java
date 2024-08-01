package org.akip.service;

import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.service.dto.ProcessInstanceNotificationDTO;
import org.akip.service.mapper.ProcessInstanceNotificationMapper;
import org.akip.domain.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public void save(ProcessInstance processInstance, ProcessInstanceEventType notificationType, String subscriberId) {
        ProcessInstanceNotification processInstanceNotification = new ProcessInstanceNotification();

        if (notificationType == ProcessInstanceEventType.TASK_COMPLETED) {
            processInstanceNotification.setTitle("Completed Task Notification");
            processInstanceNotification.setDescription(
                "A completed task from the " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " +
                processInstance.getBusinessKey()
            );
        }
        if (notificationType == ProcessInstanceEventType.NOTE_ADDED) {
            processInstanceNotification.setTitle("Note Added Notification");
            processInstanceNotification.setDescription(
                "A new note has been added to the process " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " +
                processInstance.getBusinessKey()
            );
        }
        if (notificationType == ProcessInstanceEventType.NOTE_EDITED) {
            processInstanceNotification.setTitle("Note Edited Notification");
            processInstanceNotification.setDescription(
                    "A note in the process " +
                            processInstance.getProcessDefinition().getName() +
                            " with the instance: " +
                            processInstance.getBusinessKey() +
                            ", which you signed, has been edited."
            );
        }
        if (notificationType == ProcessInstanceEventType.NOTE_REMOVED) {
            processInstanceNotification.setTitle("Note Removed Notification");
            processInstanceNotification.setDescription(
                    "A note in the process " +
                            processInstance.getProcessDefinition().getName() +
                            " with the instance: " +
                            processInstance.getBusinessKey() +
                            ", which you signed, has been removed."
            );
        }
        if (notificationType == ProcessInstanceEventType.ATTACHMENT_ADDED) {
            processInstanceNotification.setTitle("Attachment Added Notification");
            processInstanceNotification.setDescription(
                "A new attachment has been added to the process " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " +
                processInstance.getBusinessKey()
            );
        }
        if (notificationType == ProcessInstanceEventType.ATTACHMENT_EDITED) {
            processInstanceNotification.setTitle("Attachment Edited Notification");
            processInstanceNotification.setDescription(
                    "An attachment in the process " +
                            processInstance.getProcessDefinition().getName() +
                            " with the instance: " +
                            processInstance.getBusinessKey() +
                            ", which you signed, has been edited"
            );
        }
        if (notificationType == ProcessInstanceEventType.ATTACHMENT_REMOVED) {
            processInstanceNotification.setTitle("Attachment Removed Notification");
            processInstanceNotification.setDescription(
                    "An attachment in the process " +
                            processInstance.getProcessDefinition().getName() +
                            " with the instance: " +
                            processInstance.getBusinessKey() +
                            ", which you signed, has been removed."
            );
        }
        processInstanceNotification.setDate(LocalDate.now());
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
    public List<ProcessInstanceNotificationDTO> findTop6BySubscriberIdOrderByIdDesc(String subscriberId) {
        log.debug("Request to get all ProcessInstanceNotifications SubscriberId");
        return processInstanceNotificationRepository
            .findTop6BySubscriberIdOrderByIdDesc(subscriberId)
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
    public Long countBySubscriberIdAndStatus(String subscriberId) {
        log.debug("Request to get all ProcessInstanceNotificationsUnread SubscriberId");
        return processInstanceNotificationRepository
                .countBySubscriberIdAndStatus(subscriberId, ProcessInstanceNotificationStatus.UNREAD);
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
     * Delete the processInstanceNotification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessInstanceNotification : {}", id);
        processInstanceNotificationRepository.deleteById(id);
    }
}
