package org.akip.service;


import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceNotificationType;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final ProcessInstanceMailService processInstanceMailService;

    private final ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository;

    private final UserResolver userResolver;

    private final ProcessInstanceNotificationService processInstanceNotificationService;

    private final ProcessInstanceRepository processInstanceRepository;

    public SubscriptionService(
            ProcessInstanceMailService processInstanceMailService,
            ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
            UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceMapper processInstanceMapper,
            ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceMailService = processInstanceMailService;
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
        this.userResolver = userResolver;
        this.processInstanceNotificationService = processInstanceNotificationService;
        this.processInstanceRepository = processInstanceRepository;
    }

    public void notifyCompletedTask(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyTasks;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/taskCompletedEventEmail",
                "email.taskCompletedEvent.title",
                ProcessInstanceNotificationType.TASK_COMPLETED
        );
    }

    public void notifyAddedNote(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyNotes;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/noteAddedEventEmail",
                "email.noteAddedEvent.title",
                ProcessInstanceNotificationType.NOTE_ADDED
        );
    }

    public void notifyChangedNote(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyNotes;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/noteChangedEventEmail",
                "email.noteChangedEvent.title",
                ProcessInstanceNotificationType.NOTE_EDITED
        );
    }

    public void notifyRemovedNote(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyNotes;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/noteRemovedEventEmail",
                "email.noteRemovedEvent.title",
                ProcessInstanceNotificationType.NOTE_REMOVED
        );
    }

    public void notifyAddedAttachment(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyAttachments;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/attachmentAddedEventEmail",
                "email.attachmentAddedEvent.title",
                ProcessInstanceNotificationType.ATTACHMENT_ADDED
        );
    }

    public void notifyChangedAttachment(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyAttachments;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/attachmentChangedEventEmail",
                "email.attachmentChangedEvent.title",
                ProcessInstanceNotificationType.ATTACHMENT_EDITED
        );
    }

    public void notifyRemovedAttachment(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyAttachments;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/attachmentRemovedEventEmail",
                "email.attachmentRemovedEvent.title",
                ProcessInstanceNotificationType.ATTACHMENT_REMOVED
        );
    }

    public void notifySubscriberChat(Long processInstanceId) {
        Predicate<ProcessInstanceSubscription> filter = ProcessInstanceSubscription::getNotifyChats;
        notifyUsers(
                filter,
                processInstanceId,
                "mail/subscriberChatEmail",
                "email.subscriptionChat.title",
                ProcessInstanceNotificationType.CHAT_MESSAGE_SENT
        );
    }

    private void notifyUsers(Predicate<ProcessInstanceSubscription> filter, Long processInstanceId, String emailTemplate, String emailTitle, ProcessInstanceNotificationType notificationType) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(processInstanceId)
                .stream()
                .filter(filter)
                .collect(Collectors.toList());

        List<String> userLoginList = processInstanceSubscriptions
                .stream()
                .map(ProcessInstanceSubscription::getSubscriberId)
                .collect(Collectors.toList());

        List<AkipUserDTO> users = userResolver.getUsersByLogins(userLoginList);

        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).get();

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(user, processInstance, emailTemplate, emailTitle);
            processInstanceNotificationService.save(processInstance, notificationType, user.getLogin());
        }
    }
}
