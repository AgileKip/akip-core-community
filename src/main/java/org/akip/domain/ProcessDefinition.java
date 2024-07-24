package org.akip.domain;

import org.akip.domain.enumeration.ProcessType;
import org.akip.domain.enumeration.StatusProcessDefinition;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A ProcessDefinition.
 */
@Entity
@Table(name = "process_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcessDefinition status;

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    @Column(name = "can_be_manually_started")
    private Boolean canBeManuallyStarted;

    @Column(name = "start_form_is_enabled")
    private Boolean startFormIsEnabled;

    @ManyToOne
    private FormDefinition startFormDefinition;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_type")
    private ProcessType processType;

    @ManyToOne
    private KipApp kipApp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessDefinition id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusProcessDefinition getStatus() {
        return this.status;
    }

    public void setStatus(StatusProcessDefinition status) {
        this.status = status;
    }

    public String getBpmnProcessDefinitionId() {
        return this.bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public Boolean getCanBeManuallyStarted() {
        return canBeManuallyStarted;
    }

    public void setCanBeManuallyStarted(Boolean canBeManuallyStarted) {
        this.canBeManuallyStarted = canBeManuallyStarted;
    }

    public Boolean getStartFormIsEnabled() {
        return startFormIsEnabled;
    }

    public void setStartFormIsEnabled(Boolean startFormIsEnabled) {
        this.startFormIsEnabled = startFormIsEnabled;
    }

    public FormDefinition getStartFormDefinition() {
        return startFormDefinition;
    }

    public void setStartFormDefinition(FormDefinition startFormDefinition) {
        this.startFormDefinition = startFormDefinition;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public KipApp getKipApp() {
        return kipApp;
    }

    public void setKipApp(KipApp kipApp) {
        this.kipApp = kipApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinition)) {
            return false;
        }
        return id != null && id.equals(((ProcessDefinition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            "}";
    }
}
