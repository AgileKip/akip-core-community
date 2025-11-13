package org.akip.delegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChangeExpressionToGetVariableWarningDelegate {

    private final Logger log = LoggerFactory.getLogger(ChangeExpressionToGetVariableWarningDelegate.class);

    public void castWarning() {

        log.warn("###########################################################");

        log.warn("""
                The way to inject variables into service and message tasks has changed. You must now use delegateExecution.getVariable("<variable>") instead of Expression. Additionally, in Camunda Modeler, variables should be added under "Input" instead of "Field Injection".
                
                The old method of injecting variables using Expression will be deprecated on 04/13/2026. Please update to the new approach before that date.
                
                To disable this warning, set the property akip.change-expression-to-get-variable-warning.enabled to true in the corresponding .yml configuration file.""");

        log.warn("###########################################################");

    }

}
