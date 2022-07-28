package org.akip.delegate;

import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.util.MD5SumUtil;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class AkipConfigureAnonymousTaskDelegate implements JavaDelegate {

    private static final long DEFAULT_TOKEN_EXPIRATION_DURATION = 15;

    private final Logger log = LoggerFactory.getLogger(AkipConfigureAnonymousTaskDelegate.class);

    private final ProcessInstanceRepository processInstanceRepository;

    private MD5SumUtil md5SumUtil = new MD5SumUtil();

    private Random random = new Random();

    private Expression tokenDuration;

    public AkipConfigureAnonymousTaskDelegate(ProcessInstanceRepository processInstanceRepository) {
        this.processInstanceRepository = processInstanceRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.debug("Generating Access Token for the ProcessInstance {}", delegateExecution.getProcessInstanceId());

        ProcessInstance processInstance = processInstanceRepository
                .findByCamundaProcessInstanceId(delegateExecution.getProcessInstanceId())
                .orElseThrow();
        processInstance.setAccessTokenNumber(buildAccessTokenNumber(processInstance.getId()));
        processInstance.setAccessTokenExpirationDate(buildAccessTokenExpirationDate(delegateExecution));
        processInstanceRepository.save(processInstance);

        log.debug("Access Token successfully generated for the ProcessInstance {}", delegateExecution.getProcessInstanceId());
    }

    private LocalDate buildAccessTokenExpirationDate(DelegateExecution delegateExecution) {
        return LocalDate.now().plusDays(getTokenExpirationDuration(delegateExecution));
    }

    private long getTokenExpirationDuration(DelegateExecution delegateExecution) {
        if (tokenDuration == null) {
            return DEFAULT_TOKEN_EXPIRATION_DURATION;
        }

        return Long.parseLong( (String) tokenDuration.getValue(delegateExecution));
    }

    private String buildAccessTokenNumber(Long id) {
        return md5SumUtil.calculateMD5Sum(id + "::" + random.nextDouble());
    }

    public void setTokenDuration(Expression tokenDuration) {
        this.tokenDuration = tokenDuration;
    }
}
