package org.akip.camunda;

import org.akip.domain.ProcessInstance;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.ProcessCancelationHandler;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Component
public class CamundaProcessInstanceEndListener implements ExecutionListener {

    private final ProcessInstanceRepository processInstanceRepository;
    private final ApplicationContext applicationContext;

    public CamundaProcessInstanceEndListener(ProcessInstanceRepository processInstanceRepository, ApplicationContext applicationContext) {
        this.processInstanceRepository = processInstanceRepository;
        this.applicationContext = applicationContext;
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
            if (verifyIfProcessIsCancelling(delegateExecution)) {
                isCancellingProcess(optionalProcessInstance.get());
            }
        }
    }

    /**
     * Registers a listener on the Camunda transaction to check the final state of the process.
     * This is crucial because the cancellation reason (deleteReason) is only reliably available
     * in the history when the main transaction is about to be committed.
     *
     * @param processInstance The domain entity to be updated.
     */
    private void isCancellingProcess(ProcessInstance processInstance) {
        Map<String, ProcessCancelationHandler> daos = applicationContext.getBeansOfType(ProcessCancelationHandler.class);
        daos
                .values()
                .stream()
                .filter(
                        handler -> handler.getProcessDefinitionKey().equals(processInstance.getProcessDefinition().getBpmnProcessDefinitionId())
                )
                .findFirst()
                .ifPresent(handler -> handler.onCancelation(processInstance.getId()));
    }

    /**
     * verify if process is canceling by the DeleteReason.
     * @return a boolean.
     */
    private boolean verifyIfProcessIsCancelling(DelegateExecution delegateExecution) {
        return (
                ((ExecutionEntity) delegateExecution).getDeleteReason() != null &&
                        ((ExecutionEntity) delegateExecution).getDeleteReason() != null
        );
    }
}
