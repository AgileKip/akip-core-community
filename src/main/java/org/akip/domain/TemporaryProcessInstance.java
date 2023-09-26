package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A TemporaryProcessInstance.
 */
@Entity
@Table(name = "temporary_process_instance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TemporaryProcessInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "process_instance_id")
    private ProcessInstance processInstanceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemporaryProcessInstance id(Long id) {
        this.id = id;
        return this;
    }

    public String getBpmnProcessDefinitionId() {
        return this.bpmnProcessDefinitionId;
    }

    public TemporaryProcessInstance bpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
        return this;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public TemporaryProcessInstance startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public TemporaryProcessInstance endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return this.status;
    }

    public TemporaryProcessInstance status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProcessInstance getProcessInstance() {
        return processInstanceId;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstanceId = processInstance;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemporaryProcessInstance)) {
            return false;
        }
        return id != null && id.equals(((TemporaryProcessInstance) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemporaryProcessInstance{" +
            "id=" + getId() +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
