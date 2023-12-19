package org.akip.service.dto;

import org.akip.domain.ProcessDefinition;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.TaskDefinition} entity.
 */
public class TaskDefinitionDTO implements Serializable {

    private Long id;

    private String taskId;

    private String name;

    @Lob
    private String documentation;

    private String assignee;

    private String candidateUsers;

    private String candidateGroups;

    private String priority;

    @Lob
    private Map<String, String> props;
    @Lob
    private Map<String, String> formFields;

    private String bpmnProcessDefinitionId;

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

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Map<String, String> getFormFields() {
        return formFields;
    }

    public void setFormFields(Map<String, String> formFields) {
        this.formFields = formFields;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDefinitionDTO that = (TaskDefinitionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(taskId, that.taskId) && Objects.equals(name, that.name) && Objects.equals(documentation, that.documentation) && Objects.equals(assignee, that.assignee) && Objects.equals(candidateUsers, that.candidateUsers) && Objects.equals(candidateGroups, that.candidateGroups) && Objects.equals(priority, that.priority) && Objects.equals(props, that.props) && Objects.equals(formFields, that.formFields) && Objects.equals(bpmnProcessDefinitionId, that.bpmnProcessDefinitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, name, documentation, assignee, candidateUsers, candidateGroups, priority, props, formFields, bpmnProcessDefinitionId);
    }

    @Override
    public String toString() {
        return "TaskDefinitionDTO{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", documentation='" + documentation + '\'' +
                ", assignee='" + assignee + '\'' +
                ", candidateUsers='" + candidateUsers + '\'' +
                ", candidateGroups='" + candidateGroups + '\'' +
                ", priority='" + priority + '\'' +
                ", props=" + props +
                ", formFields=" + formFields +
                ", bpmnProcessDefinitionId='" + bpmnProcessDefinitionId + '\'' +
                '}';
    }
}
