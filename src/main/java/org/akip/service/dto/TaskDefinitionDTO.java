package org.akip.service.dto;

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

    private Integer priority;

    private Map<String, String> props;

    private String bpmnProcessDefinitionId;

    private Boolean dynamicFormIsEnabled;

    private FormDefinitionDTO formDefinition;

    public TaskDefinitionDTO() {
    }

    public TaskDefinitionDTO(String bpmnProcessDefinitionId, String taskId){
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

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
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

    public FormDefinitionDTO getFormDefinition() {
        return formDefinition;
    }

    public void setFormDefinition(FormDefinitionDTO formDefinition) {
        this.formDefinition = formDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDefinitionDTO that = (TaskDefinitionDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskDefinitionDTO{" +
                "id=" + id +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", bpmnProcessDefinitionId='" + bpmnProcessDefinitionId + '\'' +
                '}';
    }
}
