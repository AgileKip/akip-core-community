package org.akip.service.dto;

import java.util.Objects;

public class DecisionDeploymentDmnModelDTO {

    private static final long serialVersionUID = 8795521274029951279L;

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
        if (!(o instanceof DecisionDeploymentDmnModelDTO)) {
            return false;
        }

        DecisionDeploymentDmnModelDTO decisionDeploymentDTO = (DecisionDeploymentDmnModelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, decisionDeploymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
