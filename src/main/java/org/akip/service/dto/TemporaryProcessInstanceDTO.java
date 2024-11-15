package org.akip.service.dto;

import org.akip.domain.ProcessInstance;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.TemporaryProcessInstance;} entity.
 */
public class TemporaryProcessInstanceDTO implements Serializable {

    private Long id;

    private String bpmnProcessDefinitionId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String status;

    private ProcessInstance processInstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemporaryProcessInstanceDTO)) {
            return false;
        }

        TemporaryProcessInstanceDTO temporaryProcessInstanceDTO = (TemporaryProcessInstanceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, temporaryProcessInstanceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemporaryProcessInstanceDTO{" +
            "id=" + getId() +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
