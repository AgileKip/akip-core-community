package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
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
public abstract class AbstractNotificationService {

    private final ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService;
    private final ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository;
    private final UserResolver userResolver;
    private final ProcessInstanceNotificationService processInstanceNotificationService;
    private final ProcessInstanceRepository processInstanceRepository;

    public AbstractNotificationService(
            ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService,
            ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
            UserResolver userResolver,
            ProcessInstanceNotificationService processInstanceNotificationService,
            ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceSubscriptionMailService = processInstanceSubscriptionMailService;
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
        this.userResolver = userResolver;
        this.processInstanceNotificationService = processInstanceNotificationService;
        this.processInstanceRepository = processInstanceRepository;
    }

    public void notifyUsers(Object source, Long processInstanceId) {
        List<ProcessInstanceSubscription> processInstanceSubscriptions = processInstanceSubscriptionRepository.findAllByProcessInstanceId(processInstanceId)
                .stream()
                .filter(getFilter())
                .collect(Collectors.toList());

        List<String> userLoginList = processInstanceSubscriptions
                .stream()
                .map(ProcessInstanceSubscription::getSubscriberId)
                .collect(Collectors.toList());

        List<AkipUserDTO> subscribedUsers = userResolver.getUsersByLogins(userLoginList);

        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).get();

        for (AkipUserDTO subscribedUser : subscribedUsers) {
            processInstanceSubscriptionMailService.sendEmailFromTemplate(subscribedUser, processInstance, source, getEmailTemplate(), getEmailTitle());
            processInstanceNotificationService.save(source, processInstance, getNotificationType(), subscribedUser.getLogin());
        }
    }

    public ProcessInstanceNotification createNotification(Object source, ProcessInstance processInstance){
        ProcessInstanceNotification notification = new ProcessInstanceNotification();
        notification.setTitle(getTitle(source, processInstance));
        notification.setDescription(getDescription(source, processInstance));
        return notification;
    }

    protected abstract String getTitle(Object source, ProcessInstance processInstance);

    protected abstract String getDescription(Object source, ProcessInstance processInstance);

    protected abstract String getEmailTitle();

    protected abstract String getEmailTemplate();

    protected abstract ProcessInstanceEventType getNotificationType();

    protected abstract Predicate<? super ProcessInstanceSubscription> getFilter();
}
