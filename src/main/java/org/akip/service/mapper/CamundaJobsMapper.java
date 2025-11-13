package org.akip.service.mapper;

import org.akip.service.dto.CamundaJobDTO;
import org.camunda.bpm.engine.impl.persistence.entity.TimerEntity;
import org.camunda.bpm.engine.runtime.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * Mapper for the DTO {@link CamundaJobDTO}.
 */
@Component
public class CamundaJobsMapper {

    public CamundaJobDTO mapToCamundaJobDTO(Job job) {
        CamundaJobDTO camundaJobDTO = new CamundaJobDTO();

        camundaJobDTO.setId(String.valueOf((job.getId())));
        camundaJobDTO.setProcessDefinitionId(job.getProcessDefinitionId());
        camundaJobDTO.setProcessDefinitionKey(job.getProcessDefinitionKey());
        camundaJobDTO.setProcessInstanceId(job.getProcessInstanceId());
        camundaJobDTO.setFailedActivityId(job.getFailedActivityId());
        camundaJobDTO.setJobDefinitionId(job.getJobDefinitionId());
        camundaJobDTO.setCreateTime(job.getCreateTime());
        camundaJobDTO.setDueDate(job.getDuedate());
        camundaJobDTO.setRetries(job.getRetries());
        camundaJobDTO.setSuspended(job.isSuspended());
        camundaJobDTO.setPriority(job.getPriority());
        camundaJobDTO.setTenantId(job.getTenantId());
        if (job instanceof TimerEntity) {
            TimerEntity timerEntity = (TimerEntity) job;
            camundaJobDTO.setJobHandlerConfiguration(timerEntity.getJobHandlerConfigurationRaw());
        }
        camundaJobDTO.setExecutionId(job.getExecutionId());
        camundaJobDTO.setExceptionMessage(job.getExceptionMessage());

        return camundaJobDTO;
    }
}
