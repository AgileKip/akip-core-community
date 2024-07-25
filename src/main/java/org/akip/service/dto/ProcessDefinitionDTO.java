package org.akip.service.dto;

import org.akip.domain.enumeration.ProcessType;
import org.akip.domain.enumeration.StatusProcessDefinition;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessDefinition} entity.
 */
public class ProcessDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String description;

    private StatusProcessDefinition status;

    private String bpmnProcessDefinitionId;

    private Boolean canBeManuallyStarted;

    private Boolean startFormIsEnabled;

    private FormDefinitionDTO startFormDefinition;

    private ProcessType processType;

    private KipAppDTO kipApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusProcessDefinition getStatus() {
        return status;
    }

    public void setStatus(StatusProcessDefinition status) {
        this.status = status;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
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

    public FormDefinitionDTO getStartFormDefinition() {
        return startFormDefinition;
    }

    public void setStartFormDefinition(FormDefinitionDTO startFormDefinition) {
        this.startFormDefinition = startFormDefinition;
    }

    public ProcessType getProcessType() {
        return processType;
    }

    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    public KipAppDTO getKipApp() {
        return kipApp;
    }

    public void setKipApp(KipAppDTO kipApp) {
        this.kipApp = kipApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinitionDTO)) {
            return false;
        }

        ProcessDefinitionDTO processDefinitionDTO = (ProcessDefinitionDTO) o;
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
        return "ProcessDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            "}";
    }
}
