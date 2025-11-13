package org.akip.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.AkipEmailConnectorConfig} entity.
 */
public class AkipEmailConnectorConfigDTO implements Serializable {

    private Long id;

    private String name;

    @Lob
    private String mailboxExpression;

    @Lob
    private String subjectExpression;

    @Lob
    private String contentExpression;

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

    public String getMailboxExpression() {
        return mailboxExpression;
    }

    public void setMailboxExpression(String mailboxExpression) {
        this.mailboxExpression = mailboxExpression;
    }

    public String getSubjectExpression() {
        return subjectExpression;
    }

    public void setSubjectExpression(String subjectExpression) {
        this.subjectExpression = subjectExpression;
    }

    public String getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(String contentExpression) {
        this.contentExpression = contentExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipEmailConnectorConfigDTO)) {
            return false;
        }

        AkipEmailConnectorConfigDTO emailActionConfigDTO = (AkipEmailConnectorConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailActionConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipEmailConnectorConfigDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
