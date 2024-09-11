package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.resolver.AkipUserDTO;
import org.akip.service.dto.ProcessInstanceNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

import java.util.Locale;

@Service
public class ProcessInstanceNotificationMailService {

    private static final Locale defaultLocale = Locale.forLanguageTag("en");
    private static final String USER = "user";
    private static final String PROCESS_INSTANCE = "processInstance";
    private static final String NOTIFICATION_TITLE = "notification_title";
    private static final String DESCRIPTION = "description";
    private static final String NOTIFICATION = "notification";
    private final Logger log = LoggerFactory.getLogger(AkipMailService.class);
    private static final String BASE_URL = "baseUrl";
    private final JHipsterProperties jHipsterProperties;
    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final AkipMailService mailService;

    private static final String TITLE_KEY = "email.eventNotification.title";

    private static final String TEMPLATE_NAME = "mail/eventNotificationEmail";



    public ProcessInstanceNotificationMailService(
        JHipsterProperties jHipsterProperties,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine,
        AkipMailService mailService
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.mailService = mailService;
    }

    public void sendNotificationEmail(AkipUserDTO user, ProcessInstance processInstance, ProcessInstanceNotificationDTO notification) {
        log.debug("Email doesn't exist for user '{}'", processInstance.getId());
        Context context = new Context(defaultLocale);
        context.setVariable(PROCESS_INSTANCE, processInstance);
        context.setVariable(NOTIFICATION, notification);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(TEMPLATE_NAME, context);
        String subject = messageSource.getMessage(TITLE_KEY, new Object[]{notification.getTitle()}, defaultLocale);
        mailService.sendEmail(user.getEmail(), subject, content, false, true);
    }
}
