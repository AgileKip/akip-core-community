package org.akip.delegate.executor;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.akip.groovy.BindingBuilder;
import org.akip.service.AkipMailService;
import org.akip.service.dto.AkipEmailConnectorConfigDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AkipEmailConnectorExecutor {

    private final BindingBuilder bindingBuilder;

    private final AkipMailService akipMailService;

    public AkipEmailConnectorExecutor(BindingBuilder bindingBuilder, AkipMailService akipMailService) {
        this.bindingBuilder = bindingBuilder;
        this.akipMailService = akipMailService;
    }

    public AkipEmailConnectorMessageDTO buildAndSendMessageFromProcessEntity(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Object processEntity) {
        AkipEmailConnectorMessageDTO akipEmailConnectorMessage = buildMessageFromProcessEntity(akipEmailConnectorConfig, processEntity);
        akipMailService.sendEmail(akipEmailConnectorMessage.getMailboxes(), akipEmailConnectorMessage.getSubject(), akipEmailConnectorMessage.getContent());
        return akipEmailConnectorMessage;
    }

    public AkipEmailConnectorMessageDTO buildAndSendMessageFromProcessInstance(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Object processInstance) {
        AkipEmailConnectorMessageDTO akipEmailConnectorMessage = buildMessageFromProcessInstance(akipEmailConnectorConfig, processInstance);
        akipMailService.sendEmail(akipEmailConnectorMessage.getMailboxes(), akipEmailConnectorMessage.getSubject(), akipEmailConnectorMessage.getContent());
        return akipEmailConnectorMessage;
    }

    public AkipEmailConnectorMessageDTO buildMessageFromProcessEntity(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Object processEntity) {
        Binding binding = bindingBuilder.buildBindingFromProcessEntity(processEntity);
        return buildMessage(akipEmailConnectorConfig, binding);
    }

    public AkipEmailConnectorMessageDTO buildMessageFromProcessInstance(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Object processInstance) {
        Binding binding = bindingBuilder.buildBindingFromProcessInstance(processInstance);
        return buildMessage(akipEmailConnectorConfig, binding);
    }

    public AkipEmailConnectorMessageDTO buildMessage(AkipEmailConnectorConfigTestRequestDTO akipEmailConnectorTestRequest) {
        Binding binding = bindingBuilder.buildBinding(
                akipEmailConnectorTestRequest.getProcessEntityName(),
                akipEmailConnectorTestRequest.getProcessEntityId()
        );
        return buildMessage(akipEmailConnectorTestRequest.getAkipEmailConnectorConfig(), binding);
    }

    private AkipEmailConnectorMessageDTO buildMessage(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Binding binding) {
        AkipEmailConnectorMessageDTO akipEmailConnectorMessage = new AkipEmailConnectorMessageDTO();
        akipEmailConnectorMessage.setMailboxes(executeMailboxExpression(akipEmailConnectorConfig, binding));
        akipEmailConnectorMessage.setSubject(executeSubjectExpression(akipEmailConnectorConfig, binding));
        akipEmailConnectorMessage.setContent(executeContentExpression(akipEmailConnectorConfig, binding));
        return akipEmailConnectorMessage;
    }

    public String executeMailboxExpression(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Binding binding) {
        GroovyShell shell = new GroovyShell(binding);
        List<String> mails = (List<String>) shell.evaluate(akipEmailConnectorConfig.getMailboxExpression());
        return mails.stream().collect(Collectors.joining(", "));
    }

    public String executeSubjectExpression(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Binding binding) {
        GroovyShell shell = new GroovyShell(binding);
        String expression = akipEmailConnectorConfig.getSubjectExpression().trim();
        if (!expression.contains("\"")) {
            expression = "\"" + expression + "\"";
        }
        Object subject = shell.evaluate(expression);
        return subject.toString();
    }

    public String executeContentExpression(AkipEmailConnectorConfigDTO akipEmailConnectorConfig, Binding binding) {
        GroovyShell shell = new GroovyShell(binding);
        String expression = akipEmailConnectorConfig.getContentExpression();
        if (!expression.contains("\"\"\"")) {
            expression = "\"\"\"" + expression + "\"\"\"";
        }
        return shell.evaluate(expression).toString();
    }
}
