package org.akip.service.dto;

import org.akip.domain.enumeration.StatusProcessInstance;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessInstance} entity.
 */
public class ProcessInstanceDTO implements Serializable {

    private Long id;

    private String businessKey;

    private String camundaDeploymentId;

    private String camundaProcessDefinitionId;

    private String camundaProcessInstanceId;

    @Lob
    private String camundaProcessVariables;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private StatusProcessInstance status;

    private ProcessDefinitionDTO processDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaProcessDefinitionId() {
        return camundaProcessDefinitionId;
    }

    public void setCamundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
    }

    public String getCamundaProcessInstanceId() {
        return camundaProcessInstanceId;
    }

    public void setCamundaProcessInstanceId(String camundaProcessInstanceId) {
        this.camundaProcessInstanceId = camundaProcessInstanceId;
    }

    public String getCamundaProcessVariables() {
        return camundaProcessVariables;
    }

    public void setCamundaProcessVariables(String camundaProcessVariables) {
        this.camundaProcessVariables = camundaProcessVariables;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public StatusProcessInstance getStatus() {
        return status;
    }

    public void setStatus(StatusProcessInstance status) {
        this.status = status;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstanceDTO)) {
            return false;
        }

        ProcessInstanceDTO processInstanceDTO = (ProcessInstanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processInstanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceDTO{" +
            "id=" + getId() +
            ", businessKey='" + getBusinessKey() + "'" +
            ", camundaDeploymentId='" + getCamundaDeploymentId() + "'" +
            ", camundaProcessDefinitionId='" + getCamundaProcessDefinitionId() + "'" +
            ", camundaProcessInstanceId='" + getCamundaProcessInstanceId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
