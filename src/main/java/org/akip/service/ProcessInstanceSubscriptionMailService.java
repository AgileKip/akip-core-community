package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.resolver.AkipUserDTO;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.NoteDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

import java.util.Locale;

@Service
public class ProcessInstanceSubscriptionMailService {

    private static final Locale defaultLocale = Locale.forLanguageTag("en");
    private static final String USER = "user";
    private static final String PROCESS_INSTANCE = "processInstance";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String NOTIFICATION_ID = "notificationId";
    private final Logger log = LoggerFactory.getLogger(AkipMailService.class);
    private static final String BASE_URL = "baseUrl";
    private final JHipsterProperties jHipsterProperties;
    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final AkipMailService mailService;

    private static final String TITLE_KEY = "email.eventNotification.title";

    private static final String TEMPLATE_NAME = "mail/eventNotificationEmail";



    public ProcessInstanceSubscriptionMailService(
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

    public void sendEmailFromTemplate(AkipUserDTO user, ProcessInstance processInstance, Long notificationId, String title, String description) {
        log.debug("Email doesn't exist for user '{}'", processInstance.getId());
        Context context = new Context(defaultLocale);
        context.setVariable(TITLE, title);
        context.setVariable(DESCRIPTION, description);
        context.setVariable(PROCESS_INSTANCE, processInstance);
        context.setVariable(NOTIFICATION_ID, notificationId);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(TEMPLATE_NAME, context);
        String subject = messageSource.getMessage(TITLE_KEY, new Object[]{title}, defaultLocale);
        mailService.sendEmail(user.getEmail(), subject, content, false, true);
    }
}
