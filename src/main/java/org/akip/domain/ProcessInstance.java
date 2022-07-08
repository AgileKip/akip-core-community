package org.akip.domain;

import org.akip.domain.enumeration.StatusProcessInstance;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A ProcessInstance.
 */
@Entity
@Table(name = "process_instance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "business_key")
    private String businessKey;

    @Column(name = "camunda_deployment_id")
    private String camundaDeploymentId;

    @Column(name = "camunda_process_definition_id")
    private String camundaProcessDefinitionId;

    @NaturalId
    @Column(name = "camunda_process_instance_id")
    private String camundaProcessInstanceId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "camunda_process_variables")
    private String camundaProcessVariables;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "props")
    private String props;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "data")
    private String data;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcessInstance status;

    @Column(name = "access_token_number")
    private String accessTokenNumber;

    @Column(name = "access_token_expiration_date")
    private LocalDate accessTokenExpirationDate;

    @ManyToOne
    private ProcessDefinition processDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstance id(Long id) {
        this.id = id;
        return this;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public ProcessInstance businessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCamundaDeploymentId() {
        return this.camundaDeploymentId;
    }

    public ProcessInstance camundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
        return this;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaProcessDefinitionId() {
        return this.camundaProcessDefinitionId;
    }

    public ProcessInstance camundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
        return this;
    }

    public void setCamundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
    }

    public String getCamundaProcessInstanceId() {
        return this.camundaProcessInstanceId;
    }

    public ProcessInstance camundaProcessInstanceId(String camundaProcessInstanceId) {
        this.camundaProcessInstanceId = camundaProcessInstanceId;
        return this;
    }

    public void setCamundaProcessInstanceId(String camundaProcessInstanceId) {
        this.camundaProcessInstanceId = camundaProcessInstanceId;
    }

    public String getCamundaProcessVariables() {
        return this.camundaProcessVariables;
    }

    public ProcessInstance camundaProcessVariables(String camundaProcessVariables) {
        this.camundaProcessVariables = camundaProcessVariables;
        return this;
    }

    public void setCamundaProcessVariables(String camundaProcessVariables) {
        this.camundaProcessVariables = camundaProcessVariables;
    }

    public String getProps() {
        return props;
    }

    public ProcessInstance props(String props) {
        this.props = props;
        return this;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getData() {
        return data;
    }

    public ProcessInstance data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public ProcessInstance startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public ProcessInstance endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public StatusProcessInstance getStatus() {
        return this.status;
    }

    public ProcessInstance status(StatusProcessInstance status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProcessInstance status) {
        this.status = status;
    }

    public String getAccessTokenNumber() {
        return accessTokenNumber;
    }

    public ProcessInstance accessTokenNumber(String accessTokenNumber) {
        this.accessTokenNumber = accessTokenNumber;
        return this;
    }

    public void setAccessTokenNumber(String accessTokenNumber) {
        this.accessTokenNumber = accessTokenNumber;
    }

    public LocalDate getAccessTokenExpirationDate() {
        return accessTokenExpirationDate;
    }

    public ProcessInstance accessTokenExpirationDate(LocalDate accessTokenExpirationDate) {
        this.accessTokenExpirationDate = accessTokenExpirationDate;
        return this;
    }

    public void setAccessTokenExpirationDate(LocalDate accessTokenExpirationDate) {
        this.accessTokenExpirationDate = accessTokenExpirationDate;
    }

    public ProcessDefinition getProcessDefinition() {
        return this.processDefinition;
    }

    public ProcessInstance processDefinition(ProcessDefinition processDefinition) {
        this.setProcessDefinition(processDefinition);
        return this;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstance)) {
            return false;
        }
        return id != null && id.equals(((ProcessInstance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstance{" +
            "id=" + getId() +
            ", businessKey='" + getBusinessKey() + "'" +
            ", camundaDeploymentId='" + getCamundaDeploymentId() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
