package org.akip.domain;

import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A TaskInstance.
 */
@Entity
@Table(name = "task_instance")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTaskInstance status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeTaskInstance type;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "owner")
    private String owner;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "execution_id")
    private String executionId;

    @Column(name = "task_definition_key")
    private String taskDefinitionKey;

    @Column(name = "suspended")
    private Boolean suspended;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "candidateGroups")
    private String candidateGroups;

    @Column(name = "connector_name")
    private String connectorName;

    @Column(name = "connector_config_name")
    private String connectorConfigName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "form_fields")
    private String formFields;

    @ManyToOne
    private ProcessDefinition processDefinition;

    @ManyToOne
    private TaskDefinition taskDefinition;

    @ManyToOne
    @JoinColumn(name = "camundaProcessInstanceId", referencedColumnName = "camunda_process_instance_id")
    private ProcessInstance processInstance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskInstance id(Long id) {
        this.id = id;
        return this;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public TaskInstance taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return this.name;
    }

    public TaskInstance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusTaskInstance getStatus() {
        return this.status;
    }

    public TaskInstance status(StatusTaskInstance status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusTaskInstance status) {
        this.status = status;
    }

    public TypeTaskInstance getType() {
        return type;
    }

    public TaskInstance type(TypeTaskInstance type) {
        this.type = type;
        return this;
    }

    public void setType(TypeTaskInstance type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public TaskInstance description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public TaskInstance createDate(Instant createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public TaskInstance createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public TaskInstance dueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public TaskInstance startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public TaskInstance endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getOwner() {
        return this.owner;
    }

    public TaskInstance owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return this.assignee;
    }

    public TaskInstance assignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public TaskInstance executionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskDefinitionKey() {
        return this.taskDefinitionKey;
    }

    public TaskInstance taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public Boolean getSuspended() {
        return this.suspended;
    }

    public TaskInstance suspended(Boolean suspended) {
        this.suspended = suspended;
        return this;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public TaskInstance priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

    public TaskInstance candidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
        return this;
    }

    public void setCandidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public TaskInstance connectorName(String connectorName) {
        this.connectorName = connectorName;
        return this;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public String getConnectorConfigName() {
        return connectorConfigName;
    }

    public TaskInstance connectorConfigName(String connectorConfigName) {
        this.connectorConfigName = connectorConfigName;
        return this;
    }

    public void setConnectorConfigName(String connectorConfigName) {
        this.connectorConfigName = connectorConfigName;
    }

    public String getFormFields() {
        return formFields;
    }

    public TaskInstance formFields(String formFields) {
        this.formFields = formFields;
        return this;
    }

    public void setFormFields(String formFields) {
        this.formFields = formFields;
    }

    public ProcessDefinition getProcessDefinition() {
        return this.processDefinition;
    }

    public TaskInstance processDefinition(ProcessDefinition processDefinition) {
        this.setProcessDefinition(processDefinition);
        return this;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public ProcessInstance getProcessInstance() {
        return this.processInstance;
    }

    public TaskInstance processInstance(ProcessInstance processInstance) {
        this.setProcessInstance(processInstance);
        return this;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public TaskInstance processInstance(TaskDefinition taskDefinition) {
        this.setTaskDefinition(taskDefinition);
        return this;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskInstance that = (TaskInstance) o;
        return Objects.equals(id, that.id) && Objects.equals(taskId, that.taskId) && Objects.equals(name, that.name) && status == that.status && type == that.type && Objects.equals(description, that.description) && Objects.equals(createDate, that.createDate) && Objects.equals(createTime, that.createTime) && Objects.equals(dueDate, that.dueDate) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(owner, that.owner) && Objects.equals(assignee, that.assignee) && Objects.equals(executionId, that.executionId) && Objects.equals(taskDefinitionKey, that.taskDefinitionKey) && Objects.equals(suspended, that.suspended) && Objects.equals(priority, that.priority) && Objects.equals(candidateGroups, that.candidateGroups) && Objects.equals(connectorName, that.connectorName) && Objects.equals(connectorConfigName, that.connectorConfigName) && Objects.equals(formFields, that.formFields) && Objects.equals(processDefinition, that.processDefinition) && Objects.equals(taskDefinition, that.taskDefinition) && Objects.equals(processInstance, that.processInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, name, status, type, description, createDate, createTime, dueDate, startTime, endTime, owner, assignee, executionId, taskDefinitionKey, suspended, priority, candidateGroups, connectorName, connectorConfigName, formFields, processDefinition, taskDefinition, processInstance);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "TaskInstance{" +
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
                ", candidateGroups='" + candidateGroups + '\'' +
                ", connectorName='" + connectorName + '\'' +
                ", connectorConfigName='" + connectorConfigName + '\'' +
                ", formFields='" + formFields + '\'' +
                ", processDefinition=" + processDefinition +
                ", taskDefinition=" + taskDefinition +
                ", processInstance=" + processInstance +
                '}';
    }
}
