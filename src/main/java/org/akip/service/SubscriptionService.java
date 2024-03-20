package org.akip.service;


import org.akip.domain.ProcessInstanceSubscription;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.ProcessInstanceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final ProcessInstanceMailService processInstanceMailService;

    private final ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository;
    private final UserResolver userRepository;

    public SubscriptionService(
        AkipMailService mailService,
        ProcessInstanceMailService processInstanceMailService,
        ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
        UserResolver userRepository
    ) {
        this.processInstanceMailService = processInstanceMailService;
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
        this.userRepository = userRepository;
    }

    public void taskCompletedEventNotify(ProcessInstanceDTO processInstance) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            processInstance.getId()
        );

        List<ProcessInstanceSubscription> processInstanceNotifyTask = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyTasks)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyTask
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyTask.get(0).getProcessInstance(),
                "mail/taskCompletedEventEmail",
                "email.taskCompletedEvent.title"
            );
        }
    }

    public void noteAddedEventNotify(Long noteEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            noteEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyNotes = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyNotes)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyNotes
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyNotes.get(0).getProcessInstance(),
                "mail/noteAddedEventEmail",
                "email.noteAddedEvent.title"
            );
        }
    }

    public void noteChangedEventNotify(Long noteEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            noteEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyNotes = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyNotes)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyNotes
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyNotes.get(0).getProcessInstance(),
                "mail/noteChangedEventEmail",
                "email.noteChangedEvent.title"
            );
        }
    }

    public void noteRemovedEventNotify(Long noteEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            noteEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyNotes = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyNotes)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyNotes
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyNotes.get(0).getProcessInstance(),
                "mail/noteRemovedEventEmail",
                "email.noteRemovedEvent.title"
            );
        }
    }

    public void attachmentAddedEventNotify(Long attachmentEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            attachmentEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyAttachments = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyAttachments)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyAttachments
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyAttachments.get(0).getProcessInstance(),
                "mail/attachmentAddedEventEmail",
                "email.attachmentAddedEvent.title"
            );
        }
    }

    public void attachmentChangedEventNotify(Long attachmentEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            attachmentEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyAttachments = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyAttachments)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyAttachments
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyAttachments.get(0).getProcessInstance(),
                "mail/attachmentChangedEventEmail",
                "email.attachmentChangedEvent.title"
            );
        }
    }

    public void attachmentRemovedEventNotify(Long attachmentEntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            attachmentEntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyAttachments = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyAttachments)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyAttachments
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyAttachments.get(0).getProcessInstance(),
                "mail/attachmentRemovedEventEmail",
                "email.attachmentRemovedEvent.title"
            );
        }
    }

    public void chatSubscriberEventNotify(Long EntityId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(
            EntityId
        );

        List<ProcessInstanceSubscription> processInstanceNotifyChats = processInstanceSubscriptions
            .stream()
            .filter(ProcessInstanceSubscription::getNotifyChats)
            .collect(Collectors.toList());

        List<String> userLoginList = processInstanceNotifyChats
            .stream()
            .map(ProcessInstanceSubscription::getSubscriberId)
            .collect(Collectors.toList());

        List<AkipUserDTO> users = userRepository.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users) {
            processInstanceMailService.sendEmailFromTemplate(
                user,
                processInstanceNotifyChats.get(0).getProcessInstance(),
                "mail/subscriberAttachmentEmail",
                "email.subscriptionChat.title"
            );
        }
    }
}
