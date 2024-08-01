package org.akip.dao;

public class TaskDefinitionSearchDTO {

    private Long id;

    private String taskId;

    private String name;

    private String bpmnProcessDefinitionId;

    public TaskDefinitionSearchDTO(Long id, String taskId, String name, String bpmnProcessDefinitionId) {
        this.id = id;
        this.taskId = taskId;
        this.name = name;
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }
}
