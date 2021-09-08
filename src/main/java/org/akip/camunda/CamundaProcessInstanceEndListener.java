package org.akip.camunda;

import org.akip.domain.ProcessInstance;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CamundaProcessInstanceEndListener implements ExecutionListener {

    private final ProcessInstanceRepository processInstanceRepository;

    public CamundaProcessInstanceEndListener(ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        Optional<ProcessInstance> optionalProcessInstance = processInstanceRepository.findByCamundaProcessInstanceId(
            delegateExecution.getProcessInstanceId()
        );

        if (optionalProcessInstance.isPresent()) {
            ProcessInstance processInstance = optionalProcessInstance.get();
            processInstance.setStatus(StatusProcessInstance.COMPLETED);
            processInstance.setEndDate(LocalDateTime.now());
            processInstanceRepository.save(processInstance);
        }
    }
}
