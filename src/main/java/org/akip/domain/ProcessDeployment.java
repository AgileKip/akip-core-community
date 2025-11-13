package org.akip.domain;

import org.akip.domain.enumeration.ProcessVisibilityType;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A ProcessDeployment.
 */
@Entity
@Table(name = "process_deployment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessDeployment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcessDeployment status;

    @Lob
    @Column(name = "specification_file")
    private byte[] specificationFile;

    @Column(name = "specification_file_content_type")
    private String specificationFileContentType;

    @Lob
    @Column(name = "camunda_deployment_message")
    private String camundaDeploymentMessage;

    @Column(name = "camunda_deployment_id")
    private String camundaDeploymentId;

    @Column(name = "camunda_process_definition_id")
    private String camundaProcessDefinitionId;

    @Column(name = "deploy_date")
    private LocalDateTime deployDate;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @Column(name = "inactivation_date")
    private LocalDateTime inactivationDate;

    @Lob
    @Column(name = "props")
    private String props;

    @ManyToOne
    private ProcessDefinition processDefinition;

    @Enumerated(EnumType.STRING)
    @Column(name = "processVisibilityType")
    private ProcessVisibilityType processVisibilityType;

    @ManyToOne
    private Tenant tenant;


    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public ProcessVisibilityType getProcessVisibilityType() {
        return processVisibilityType;
    }

    public void setProcessVisibilityType(ProcessVisibilityType processVisibilityType) {
        this.processVisibilityType = processVisibilityType;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDeployment)) {
            return false;
        }
        return id != null && id.equals(((ProcessDeployment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDeployment{" +
            "id=" + getId() +
            ", camundaProcessDefinitionId='" + getCamundaProcessDefinitionId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
