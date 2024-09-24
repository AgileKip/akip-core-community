package org.akip.service.dto;

import org.akip.domain.enumeration.StatusProcessInstance;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessInstance} entity.
 */
public class ProcessInstanceDTO implements Serializable {

    private Long id;

    private TemporaryProcessInstanceDTO temporaryProcessInstance;

    private String businessKey;

    private String camundaDeploymentId;

    private String camundaProcessDefinitionId;

    private String camundaProcessInstanceId;

    @Lob
    private String camundaProcessVariables;

    private Map<String, String> props;

    private Map<String, String> data;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private StatusProcessInstance status;

    private String accessTokenNumber;

    private LocalDate accessTokenExpirationDate;

    private ProcessDefinitionDTO processDefinition;

    private TenantDTO tenant;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public TemporaryProcessInstanceDTO getTemporaryProcessInstance() {
        return temporaryProcessInstance;
    }

    public void setTemporaryProcessInstance(TemporaryProcessInstanceDTO temporaryProcessInstance) {
        this.temporaryProcessInstance = temporaryProcessInstance;
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

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
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

    public String getAccessTokenNumber() {
        return accessTokenNumber;
    }

    public void setAccessTokenNumber(String accessTokenNumber) {
        this.accessTokenNumber = accessTokenNumber;
    }

    public LocalDate getAccessTokenExpirationDate() {
        return accessTokenExpirationDate;
    }

    public void setAccessTokenExpirationDate(LocalDate accessTokenExpirationDate) {
        this.accessTokenExpirationDate = accessTokenExpirationDate;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
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
