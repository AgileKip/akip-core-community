package org.akip.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for representing the bpmn model of a ProcessInstance.
 */
public class ProcessInstanceBpmnModelDTO implements Serializable {

    private Long id;

    private ProcessDeploymentBpmnModelDTO processDeploymentBpmnModel;

    private List<String> runningTasksDefinitionKeys;

    private List<String> completedTasksDefinitionKeys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessDeploymentBpmnModelDTO getProcessDeploymentBpmnModel() {
        return processDeploymentBpmnModel;
    }

    public void setProcessDeploymentBpmnModel(ProcessDeploymentBpmnModelDTO processDeploymentBpmnModel) {
        this.processDeploymentBpmnModel = processDeploymentBpmnModel;
    }

    public List<String> getRunningTasksDefinitionKeys() {
        return runningTasksDefinitionKeys;
    }

    public void setRunningTasksDefinitionKeys(List<String> runningTasksDefinitionKeys) {
        this.runningTasksDefinitionKeys = runningTasksDefinitionKeys;
    }

    public List<String> getCompletedTasksDefinitionKeys() {
        return completedTasksDefinitionKeys;
    }

    public void setCompletedTasksDefinitionKeys(List<String> completedTasksDefinitionKeys) {
        this.completedTasksDefinitionKeys = completedTasksDefinitionKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstanceBpmnModelDTO)) {
            return false;
        }

        ProcessInstanceBpmnModelDTO processDeploymentDTO = (ProcessInstanceBpmnModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processDeploymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceBpmnModelDTO{" +
            "id=" + getId() +
            "}";
    }
}
