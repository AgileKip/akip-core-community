package org.akip.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for representing the bpmn model of a ProcessDeployment.
 */
public class ProcessDeploymentBpmnModelDTO implements Serializable {

    private Long id;

    private String specificationFile;

    private String specificationFileContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecificationFile() {
        return specificationFile;
    }

    public void setSpecificationFile(String specificationFile) {
        this.specificationFile = specificationFile;
    }

    public String getSpecificationFileContentType() {
        return specificationFileContentType;
    }

    public void setSpecificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDeploymentBpmnModelDTO)) {
            return false;
        }

        ProcessDeploymentBpmnModelDTO processDeploymentDTO = (ProcessDeploymentBpmnModelDTO) o;
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
        return "ProcessDeploymentBpmnModelDTO{" +
            "id=" + getId() +
            "}";
    }
}
