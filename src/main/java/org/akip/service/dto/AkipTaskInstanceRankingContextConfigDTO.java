package org.akip.service.dto;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.AkipTaskInstanceRankingContextConfig} entity.
 */
public class AkipTaskInstanceRankingContextConfigDTO implements Serializable {

    private Long id;

    @Lob
    private String contextValueExpression;

    private Long processDefinitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContextValueExpression() {
        return contextValueExpression;
    }

    public void setContextValueExpression(String contextValueExpression) {
        this.contextValueExpression = contextValueExpression;
    }

    public Long getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(Long processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipTaskInstanceRankingContextConfigDTO)) {
            return false;
        }

        AkipTaskInstanceRankingContextConfigDTO akipTaskInstanceRankingContextConfigDTO = (AkipTaskInstanceRankingContextConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, akipTaskInstanceRankingContextConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipTaskInstanceRankingContextConfigDTO{" +
            "id=" + getId() +
            ", contextValueExpression='" + getContextValueExpression() + "'" +
            "}";
    }
}
