package org.akip.camunda;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class CamundaTaskDeleteListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {}
}
