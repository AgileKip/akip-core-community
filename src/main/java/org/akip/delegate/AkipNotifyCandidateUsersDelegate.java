package org.akip.delegate;

import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.akip.service.MailService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class AkipNotifyCandidateUsersDelegate implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(AkipNotifyCandidateUsersDelegate.class);

    private final UserResolver userResolver;
    private final MailService emailService;

    private final Environment env;

    private final SpringTemplateEngine templateEngine;

    private final MessageSource messageSource;

    public AkipNotifyCandidateUsersDelegate(
            UserResolver userResolver, MailService emailService,
            Environment env,
            SpringTemplateEngine templateEngine,
            MessageSource messageSource
    ) {
        this.userResolver = userResolver;
        this.emailService = emailService;
        this.env = env;
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
    }
 
    @Override
    public void notify(DelegateTask delegateTask) {
        this.log.debug("###########################################################");
        if (delegateTask.getCandidates() == null) {
            return;
        }

        List<String> authoritiesList = new ArrayList<>();
        for (IdentityLink authority : delegateTask.getCandidates()) {
            authoritiesList.add(authority.getGroupId());
        }
        List<AkipUserDTO> users = userResolver.getUsersByLogin(authoritiesList);

        if (users.isEmpty()) {
            return;
        }
        users.forEach(
                user -> {
                    String templateName = "mail/notifyNewTaskToCandidateUsersEmail";
                    String titleKey = "email.notifyNewTaskToCandidateUsersEmail.subject";
                    Locale locale = Locale.forLanguageTag(user.getLangKey());
                    Context context = new Context(locale);
                    context.setVariable("user", user);
                    context.setVariable("delegateTask", delegateTask);
                    context.setVariable("baseUrl", env.getProperty("jhipster.mail.base-url"));
                    String subject = messageSource.getMessage(titleKey, null, locale);
                    String content = templateEngine.process(templateName, context);
                    emailService.sendEmail(user.getEmail(), subject, content, false, true);
                    this.log.debug("###########################################################");
                }
        );
    }
}
