package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;

public abstract class AbstractNotificationService {

    public abstract ProcessInstanceNotification createNotification(Long eventId, ProcessInstance processInstance);
}
