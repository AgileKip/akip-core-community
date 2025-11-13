package org.akip.domain;

import org.akip.domain.enumeration.StatusDecisionDeployment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "decision_deployment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DecisionDeployment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusDecisionDeployment status;

    @Lob
    @Column(name = "specification_file")
    private byte[] specificationFile;

    @Column(name = "specification_file_c_type")
    private String specificationFileContentType;

    @Lob
    @Column(name = "camunda_deployment_message")
    private String camundaDeploymentMessage;

    @Column(name = "camunda_deployment_id")
    private String camundaDeploymentId;

    @Column(name = "camunda_decision_definition_id")
    private String camundaDecisionDefinitionId;

    @Column(name = "deploy_date")
    private LocalDateTime deployDate;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @Column(name = "inactivation_date")
    private LocalDateTime inactivationDate;

    @ManyToOne
    private DecisionDefinition decisionDefinition;

    @ManyToOne
    private Tenant tenant;

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

    public DecisionDefinition getDecisionDefinition() {
        return decisionDefinition;
    }

    public void setDecisionDefinition(DecisionDefinition decisionDefinition) {
        this.decisionDefinition = decisionDefinition;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecisionDeployment)) {
            return false;
        }
        return id != null && id.equals(((DecisionDeployment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "DecisionDeployment{" +
                "id=" + id +
                ", status=" + status +
                ", camundaDecisionDefinitionId='" + camundaDecisionDefinitionId + '\'' +
                '}';
    }
}
