package org.akip.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.camunda.CamundaConstants;
import org.akip.llm.chatgpt.ChatGPTService;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AkipLlmDelegate implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(AkipLlmDelegate.class);

    private final ChatGPTService chatGPTService;

    private final ProcessInstanceRepository processInstanceRepository;

    private  final ProcessInstanceMapper processInstanceMapper;

    private Expression promptConfigurationName;

    private Expression resultVariable;

    public AkipLlmDelegate(ChatGPTService chatGPTService, ProcessInstanceRepository processInstanceRepository, ProcessInstanceMapper processInstanceMapper) {
        this.chatGPTService = chatGPTService;
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ProcessInstanceDTO processInstance = (ProcessInstanceDTO) delegateExecution.getVariable(CamundaConstants.PROCESS_INSTANCE);

        LlmRequestDTO llmRequest = new LlmRequestDTO();
        llmRequest.setPromptConfigurationName((String) promptConfigurationName.getValue(delegateExecution));
        llmRequest.setContext(new HashMap<>());
        llmRequest.getContext().put(CamundaConstants.PROCESS_INSTANCE, processInstance);
        LlmResponseDTO chatResponse = chatGPTService.complete(llmRequest);
        processInstance.getData().put((String) resultVariable.getValue(delegateExecution), chatResponse.getContent());
        delegateExecution.setVariable(CamundaConstants.PROCESS_INSTANCE, processInstance);
        synchronizeProcessInstance(processInstance);
        this.log.debug("###########################################################");

    }

    private void synchronizeProcessInstance(ProcessInstanceDTO processInstance) throws JsonProcessingException {
        String dataAsString = processInstanceMapper.mapToString(processInstance.getData());
        processInstanceRepository.updateDataById(dataAsString, processInstance.getId());
    }
}
