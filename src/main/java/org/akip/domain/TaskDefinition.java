package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "task_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "documentation")
    private String documentation;

    @Column(name = "assignee")
    private String assignee;

    @Lob
    @Column(name = "candidate_users")
    private String candidateUsers;

    @Lob
    @Column(name = "candidate_groups")
    private String candidateGroups;

    @Column(name = "priority")
    private Integer priority;

    @Lob
    @Column(name = "props")
    private String props;

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    @Column(name = "dynamic_form_is_enabled")
    private Boolean dynamicFormIsEnabled;

    @ManyToOne
    private FormDefinition formDefinition;

    public TaskDefinition(){}

    public TaskDefinition(String bpmnProcessDefinitionId, String taskId){
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
        this.taskId = taskId;
    }

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

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(String candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public Boolean getDynamicFormIsEnabled() {
        return dynamicFormIsEnabled;
    }

    public void setDynamicFormIsEnabled(Boolean dynamicFormIsEnabled) {
        this.dynamicFormIsEnabled = dynamicFormIsEnabled;
    }

    public FormDefinition getFormDefinition() {
        return formDefinition;
    }

    public void setFormDefinition(FormDefinition formDefinition) {
        this.formDefinition = formDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDefinition that = (TaskDefinition) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskDefinition{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", bpmnProcessDefinitionId='" + bpmnProcessDefinitionId + '\'' +
                '}';
    }
}
