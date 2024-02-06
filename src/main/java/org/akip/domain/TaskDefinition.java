package org.akip.domain;

import org.apache.tools.ant.Task;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "documentation")
    private String documentation;

    @Column(name = "assignee")
    private String assignee;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "candidate_users")
    private String candidateUsers;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "candidate_groups")
    private String candidateGroups;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "form_builder")
    private String formBuilder;

    @Column(name = "form_version")
    private String formVersion;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "form_schema")
    private String formSchema;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "props")
    private String props;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "form_fields")
    private String formFields;

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

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

    public TaskDefinition id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskDefinition name(String name) {
        this.name = name;
        return this;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public TaskDefinition documentation(String documentation) {
        this.documentation = documentation;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public TaskDefinition assignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public String getCandidateUsers() {
        return candidateUsers;
    }

    public void setCandidateUsers(String candidateUsers) {
        this.candidateUsers = candidateUsers;
    }

    public TaskDefinition candidateUsers(String candidateUsers) {
        this.candidateUsers = candidateUsers;
        return this;
    }

    public String getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public TaskDefinition candidateGroups(String candidateGroups) {
        this.candidateGroups = candidateGroups;
        return this;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public TaskDefinition props(String props) {
        this.props = props;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public TaskDefinition taskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public TaskDefinition priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getFormFields() {
        return formFields;
    }

    public void setFormFields(String formFields) {
        this.formFields = formFields;
    }

    public TaskDefinition formFields(String formFields) {
        this.formFields = formFields;
        return this;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    public TaskDefinition bpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
        return this;
    }

    public String getFormBuilder() {
        return formBuilder;
    }

    public void setFormBuilder(String formBuilder) {
        this.formBuilder = formBuilder;
    }

    public TaskDefinition formBuilder(String formBuilder) {
        this.formBuilder = formBuilder;
        return this;
    }

    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }

    public TaskDefinition formVersion(String formVersion) {
        this.formVersion = formVersion;
        return this;
    }

    public String getFormSchema() {
        return formSchema;
    }

    public void setFormSchema(String formSchema) {
        this.formSchema = formSchema;
    }

    public TaskDefinition formSchema(String formSchema) {
        this.formSchema = formSchema;
        return this;
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
                '}';
    }
}