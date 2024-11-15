package org.akip.service.dto;

import org.akip.domain.enumeration.StatusDecisionDeployment;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class DecisionDeploymentDTO implements Serializable {

    private static final long serialVersionUID = 3922166925524979466L;

    private Long id;

    private StatusDecisionDeployment status;

    private byte[] specificationFile;

    private String specificationFileContentType;

    private String camundaDeploymentMessage;

    private String camundaDeploymentId;

    private String camundaDecisionDefinitionId;

    private LocalDateTime deployDate;

    private LocalDateTime activationDate;

    private LocalDateTime inactivationDate;

    private DecisionDefinitionDTO decisionDefinition;

    private TenantDTO tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusDecisionDeployment getStatus() {
        return status;
    }

    public void setStatus(StatusDecisionDeployment status) {
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

    public String getCamundaDecisionDefinitionId() {
        return camundaDecisionDefinitionId;
    }

    public void setCamundaDecisionDefinitionId(String camundaDecisionDefinitionId) {
        this.camundaDecisionDefinitionId = camundaDecisionDefinitionId;
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

    public DecisionDefinitionDTO getDecisionDefinition() {
        return decisionDefinition;
    }

    public void setDecisionDefinition(DecisionDefinitionDTO decisionDefinition) {
        this.decisionDefinition = decisionDefinition;
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
        if (!(o instanceof DecisionDeploymentDTO)) {
            return false;
        }

        DecisionDeploymentDTO decisionDeploymentDTO = (DecisionDeploymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, decisionDeploymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
