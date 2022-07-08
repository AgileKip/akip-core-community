package org.akip.delegate;

import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AkipConfigureAnonymousTaskDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(AkipConfigureAnonymousTaskDelegate.class);

    private final ProcessInstanceRepository processInstanceRepository;

    public AkipConfigureAnonymousTaskDelegate(ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.debug("Generating Access Token for the ProcessInstance {}", delegateExecution.getProcessInstanceId());
        ProcessInstance processInstance = processInstanceRepository.findByCamundaProcessInstanceId(delegateExecution.getProcessInstanceId()).orElseThrow();
        processInstance.setAccessTokenNumber(buildAccessTokenNumber());
        processInstance.setAccessTokenExpirationDate(buildAccessTokenExpirationDate());
        log.debug("Access Token successfully generated for the ProcessInstance {}", delegateExecution.getProcessInstanceId());
        processInstanceRepository.save(processInstance);
    }

    private LocalDate buildAccessTokenExpirationDate() {
        return LocalDate.now().plusDays(15);
    }

    private String buildAccessTokenNumber() {
        return "12345";
    }

}
