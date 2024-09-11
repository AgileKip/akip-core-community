package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A ProcessInstanceNotification.
 */
@Entity
@Table(name = "process_instance_notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessInstanceNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "read_date")
    private LocalDateTime readDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessInstanceNotificationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private ProcessInstanceEventType eventType;

    @Column(name = "subscriber_id")
    private String subscriberId;

    @ManyToOne
    @JsonIgnoreProperties(value = { "processDefinition" }, allowSetters = true)
    private ProcessInstance processInstance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstanceNotification id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ProcessInstanceNotification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public ProcessInstanceNotification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public ProcessInstanceNotification date(LocalDateTime date) {
        this.createDate = date;
        return this;
    }

    public LocalDateTime getReadDate() { return readDate; }

    public void setReadDate(LocalDateTime readDate) { this.readDate = readDate; }

    public void setCreateDate(LocalDateTime date) {
        this.createDate = date;
    }

    public ProcessInstanceNotificationStatus getStatus() {
        return this.status;
    }

    public ProcessInstanceNotification status(ProcessInstanceNotificationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProcessInstanceNotificationStatus status) {
        this.status = status;
    }

    public ProcessInstanceEventType getEventType() {
        return this.eventType;
    }

    public ProcessInstanceNotification eventType(ProcessInstanceEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public void setEventType(ProcessInstanceEventType eventType) {
        this.eventType = eventType;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public ProcessInstanceNotification subscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstanceNotification)) {
            return false;
        }
        return id != null && id.equals(((ProcessInstanceNotification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceNotification{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
