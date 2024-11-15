package org.akip.delegate;

import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.akip.service.AkipMailService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class AkipNotifyCandidateGroupsDelegate implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(AkipNotifyCandidateGroupsDelegate.class);

    private final UserResolver userResolver;

    private final AkipMailService emailService;

    private final Environment env;

    private final SpringTemplateEngine templateEngine;

    private final MessageSource messageSource;

    @Value("${spring.application.name}")
    private String applicationName;

    public AkipNotifyCandidateGroupsDelegate(
            AkipMailService emailService, UserResolver userResolver, Environment env,
            SpringTemplateEngine templateEngine,
            MessageSource messageSource
    ) {
        this.emailService = emailService;
        this.userResolver = userResolver;
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
        List<AkipUserDTO> users = userResolver.getUsersByAuthorities(authoritiesList);

        if (users.isEmpty()) {
            return;
        }

        users.forEach(
                user -> {
                    String templateName = "mail/notifyNewTaskToCandidateGroupsEmail";
                    String titleKey = "akip.email.notifyNewTaskToCandidateGroupsEmail.subject";
                    Locale locale = Locale.forLanguageTag(user.getLangKey());
                    Context context = new Context(locale);
                    context.setVariable("user", user);
                    context.setVariable("delegateTask", delegateTask);
                    context.setVariable("baseUrl", env.getProperty("jhipster.mail.base-url"));

                    String subject = messageSource.getMessage(titleKey, new Object[]{applicationName}, "You have a new task in the " + applicationName + "!", locale);
                    context.setVariable("title", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.title", new Object[]{applicationName}, "New task to you in the " + applicationName, locale));
                    context.setVariable("greetings", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.greeting", new Object[]{user.getFirstName()}, "Dear " + user.getFirstName(), locale));
                    context.setVariable("text1", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.text1", new Object[]{"<strong>" + delegateTask.getName() + "</strong>", "<strong>" + delegateTask.getExecution().getBusinessKey() + "</strong>", new Object[]{applicationName}}, "You have a new task <strong>" + delegateTask.getName() + "</strong> in the process with the businessKey " + "<strong>" + delegateTask.getExecution().getBusinessKey() + "</strong> in the KIP4You Platform", locale));
                    context.setVariable("text2", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.text2", null, "You can complete the task in the link below:", locale));
                    context.setVariable("myTasks", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.myTasks", null, "My Tasks", locale));
                    context.setVariable("regards", messageSource.getMessage("akip.email.notifyNewTaskToCandidateGroupsEmail.regards", null, "Regards,", locale));
                    context.setVariable("signature", messageSource.getMessage("akip.email.signature", new Object[]{applicationName}, applicationName, locale));

                    String content = templateEngine.process(templateName, context);
                    emailService.sendEmail(user.getEmail(), subject, content, false, true);
                    this.log.debug("###########################################################");
                }
        );
    }

}
