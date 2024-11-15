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

    private Expression slackUrl;
    private Expression slackMessage;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("AkipSlackWebsocketConnectorDelegate connector...");
        Map<String, String> messageBuilder = new HashMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        messageBuilder.put("text", (String) slackMessage.getValue(delegateExecution));
        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageBuilder, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity((String) slackUrl.getValue(delegateExecution), request, String.class);
    }
}
