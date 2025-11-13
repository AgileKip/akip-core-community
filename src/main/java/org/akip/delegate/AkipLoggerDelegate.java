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

    private final ChangeExpressionToGetVariableWarningDelegate changeExpressionToGetVariableWarningDelegate;

    private Expression message;

    public AkipLoggerDelegate(ChangeExpressionToGetVariableWarningDelegate changeExpressionToGetVariableWarningDelegate) {
        this.changeExpressionToGetVariableWarningDelegate = changeExpressionToGetVariableWarningDelegate;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("###########################################################");

        // TODO: This code will be updated in six months 25:31
        if (delegateExecution.getVariable("message") != null){
            log.debug((String) delegateExecution.getVariable("message"));
        }

        if (message != null) {
            changeExpressionToGetVariableWarningDelegate.castWarning();
            log.debug((String) message.getValue(delegateExecution));
        }

        log.debug("###########################################################");
    }

}
