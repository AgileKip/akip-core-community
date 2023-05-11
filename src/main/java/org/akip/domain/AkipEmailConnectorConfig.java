package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "akip_email_connector_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipEmailConnectorConfig {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "mailbox_expression")
    private String mailboxExpression;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "subject_expression")
    private String subjectExpression;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content_expression")
    private String contentExpression;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AkipEmailConnectorConfig id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AkipEmailConnectorConfig name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailboxExpression() {
        return this.mailboxExpression;
    }

    public AkipEmailConnectorConfig mailboxExpression(String mailboxExpression) {
        this.mailboxExpression = mailboxExpression;
        return this;
    }

    public void setMailboxExpression(String mailboxExpression) {
        this.mailboxExpression = mailboxExpression;
    }

    public String getSubjectExpression() {
        return this.subjectExpression;
    }

    public AkipEmailConnectorConfig subjectExpression(String subjectExpression) {
        this.subjectExpression = subjectExpression;
        return this;
    }

    public void setSubjectExpression(String subjectExpression) {
        this.subjectExpression = subjectExpression;
    }

    public String getContentExpression() {
        return this.contentExpression;
    }

    public AkipEmailConnectorConfig contentExpression(String contentExpression) {
        this.contentExpression = contentExpression;
        return this;
    }

    public void setContentExpression(String contentExpression) {
        this.contentExpression = contentExpression;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipEmailConnectorConfig)) {
            return false;
        }
        return id != null && id.equals(((AkipEmailConnectorConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipEmailConnectorConfig{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }
}
