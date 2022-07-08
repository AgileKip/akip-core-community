package org.akip.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class AkipLoggerDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(AkipLoggerDelegate.class);

    private Expression message;

    public AkipLoggerDelegate() {
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("###########################################################");

        if (message != null) {
            log.debug((String) message.getValue(delegateExecution));
        }

        log.debug("###########################################################");
    }

}
