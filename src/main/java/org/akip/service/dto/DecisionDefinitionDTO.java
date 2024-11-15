package org.akip.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class DecisionDefinitionDTO implements Serializable {

    private static final long serialVersionUID = -1213556574365030378L;

    private Long id;

    private String name;

    private String dmnDecisionDefinitionId;

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

    public String getDmnDecisionDefinitionId() {
        return dmnDecisionDefinitionId;
    }

    public void setDmnDecisionDefinitionId(String dmnDecisionDefinitionId) {
        this.dmnDecisionDefinitionId = dmnDecisionDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecisionDefinitionDTO)) {
            return false;
        }

        DecisionDefinitionDTO decisionDefinitionDTO = (DecisionDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, decisionDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
