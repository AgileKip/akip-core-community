package org.akip.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AkipSlackWebsocketConnectorDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(AkipSlackWebsocketConnectorDelegate.class);

    private final ChangeExpressionToGetVariableWarningDelegate changeExpressionToGetVariableWarningDelegate;

    private Expression slackUrl;
    private Expression slackMessage;

    public AkipSlackWebsocketConnectorDelegate(ChangeExpressionToGetVariableWarningDelegate changeExpressionToGetVariableWarningDelegate) {
        this.changeExpressionToGetVariableWarningDelegate = changeExpressionToGetVariableWarningDelegate;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("AkipSlackWebsocketConnectorDelegate connector...");
        Map<String, String> messageBuilder = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // TODO: This code will be updated in six months 33:37
        messageBuilder.put("text", getText(delegateExecution, messageBuilder));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        // TODO: This code will be updated in six months 41:45
        restTemplate.postForEntity(getSlackUrl(delegateExecution), request, String.class);
    }

    private String getText(DelegateExecution delegateExecution, Map<String, String> messageBuilder) {
        if (delegateExecution.getVariable("slackMessage") != null) {
            return (String) delegateExecution.getVariable("slackMessage");
        }

        changeExpressionToGetVariableWarningDelegate.castWarning();
        return (String) slackMessage.getValue(delegateExecution);
    }

    private String getSlackUrl(DelegateExecution delegateExecution) {
        if (delegateExecution.getVariable("slackUrl") != null) {
            return (String) delegateExecution.getVariable("slackUrl");
        } else {
            changeExpressionToGetVariableWarningDelegate.castWarning();
            return (String) slackUrl.getValue(delegateExecution);
        }
    }
}
