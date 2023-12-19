package org.akip.service.dto;

import org.akip.camunda.form.CamundaFormFieldDef;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.TaskInstance} entity.
 */
public class TaskInstanceDTO implements Serializable {

    private Long id;

    private String taskId;

    private String name;

    private StatusTaskInstance status;

    private TypeTaskInstance type;

    @Lob
    private String description;

    private Instant createDate;

    private Instant createTime;

    private Instant dueDate;

    private Instant startTime;

    private Instant endTime;

    private String owner;

    private String assignee;

    private String executionId;

    private String taskDefinitionKey;

    private Boolean suspended;

    private Integer priority;

    private List<String> candidateGroups = new ArrayList<>();

    private String connectorName;

    private String connectorConfigName;

    private List<CamundaFormFieldDef> formFields;

    private ProcessDefinitionDTO processDefinition;

    private TaskDefinitionDTO taskDefinition;

    private ProcessInstanceDTO processInstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusTaskInstance getStatus() {
        return status;
    }

    public void setStatus(StatusTaskInstance status) {
        this.status = status;
    }

    public TypeTaskInstance getType() {
        return type;
    }

    public void setType(TypeTaskInstance type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<String> getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(List<String> candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorConfigName() {
        return connectorConfigName;
    }

    public void setConnectorConfigName(String connectorConfigName) {
        this.connectorConfigName = connectorConfigName;
    }

    public List<CamundaFormFieldDef> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<CamundaFormFieldDef> formFields) {
        this.formFields = formFields;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public ProcessInstanceDTO getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDTO processInstance) {
        this.processInstance = processInstance;
    }

    public TaskDefinitionDTO getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinitionDTO taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInstanceDTO that = (TaskInstanceDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(taskId, that.taskId) && Objects.equals(name, that.name) && status == that.status && type == that.type && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate) && Objects.equals(createTime, that.createTime) && Objects.equals(dueDate, that.dueDate) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(owner, that.owner) && Objects.equals(assignee, that.assignee) && Objects.equals(executionId, that.executionId) && Objects.equals(taskDefinitionKey, that.taskDefinitionKey) && Objects.equals(suspended, that.suspended) && Objects.equals(priority, that.priority) && Objects.equals(candidateGroups, that.candidateGroups) && Objects.equals(connectorName, that.connectorName) && Objects.equals(connectorConfigName, that.connectorConfigName) && Objects.equals(formFields, that.formFields) && Objects.equals(processDefinition, that.processDefinition) && Objects.equals(taskDefinition, that.taskDefinition) && Objects.equals(processInstance, that.processInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, name, status, type, description, createDate, createTime, dueDate, startTime, endTime, owner, assignee, executionId, taskDefinitionKey, suspended, priority, candidateGroups, connectorName, connectorConfigName, formFields, processDefinition, taskDefinition, processInstance);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "TaskInstanceDTO{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", createTime=" + createTime +
                ", dueDate=" + dueDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", owner='" + owner + '\'' +
                ", assignee='" + assignee + '\'' +
                ", executionId='" + executionId + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", suspended=" + suspended +
                ", priority=" + priority +
                ", candidateGroups=" + candidateGroups +
                ", connectorName='" + connectorName + '\'' +
                ", connectorConfigName='" + connectorConfigName + '\'' +
                ", formFields=" + formFields +
                ", processDefinition=" + processDefinition +
                ", taskDefinition=" + taskDefinition +
                ", processInstance=" + processInstance +
                '}';
    }
}
