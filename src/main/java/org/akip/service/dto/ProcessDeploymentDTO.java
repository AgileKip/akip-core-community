package org.akip.service.dto;

import org.akip.domain.enumeration.ProcessVisibilityType;
import org.akip.domain.enumeration.StatusProcessDeployment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessDeployment} entity.
 */
public class ProcessDeploymentDTO implements Serializable {

    private Long id;

    private StatusProcessDeployment status;

    private byte[] specificationFile;

    private String specificationFileContentType;

    private String camundaDeploymentMessage;

    private String camundaDeploymentId;

    private String camundaProcessDefinitionId;

    private LocalDateTime deployDate;

    private LocalDateTime activationDate;

    private LocalDateTime inactivationDate;

    private Map<String, String> props;

    private ProcessDefinitionDTO processDefinition;

    private ProcessVisibilityType processVisibilityType;

    private TenantDTO tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusProcessDeployment getStatus() {
        return status;
    }

    public void setStatus(StatusProcessDeployment status) {
        this.status = status;
    }

    public byte[] getSpecificationFile() {
        return specificationFile;
    }

    public void setSpecificationFile(byte[] specificationFile) {
        this.specificationFile = specificationFile;
    }

    public String getSpecificationFileContentType() {
        return specificationFileContentType;
    }

    public void setSpecificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
    }

    public String getCamundaDeploymentMessage() {
        return camundaDeploymentMessage;
    }

    public void setCamundaDeploymentMessage(String camundaDeploymentMessage) {
        this.camundaDeploymentMessage = camundaDeploymentMessage;
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

    public LocalDateTime getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(LocalDateTime deployDate) {
        this.deployDate = deployDate;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getInactivationDate() {
        return inactivationDate;
    }

    public void setInactivationDate(LocalDateTime inactivationDate) {
        this.inactivationDate = inactivationDate;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public ProcessVisibilityType getProcessVisibilityType() {
        return processVisibilityType;
    }

    public void setProcessVisibilityType(ProcessVisibilityType processVisibilityType) {
        this.processVisibilityType = processVisibilityType;
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
        if (!(o instanceof ProcessDeploymentDTO)) {
            return false;
        }

        ProcessDeploymentDTO processDefinitionDTO = (ProcessDeploymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDeploymentDTO{" +
            "id=" + getId() +
            ", camundaProcessDefinitionId='" + camundaProcessDefinitionId + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
