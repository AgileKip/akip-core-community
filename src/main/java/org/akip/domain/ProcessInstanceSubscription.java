package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.akip.domain.enumeration.ProcessInstanceSubscriptionStatus;
import org.akip.domain.enumeration.SubscriberType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProcessInstanceSubscription.
 */
@Entity
@Table(name = "process_instance_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessInstanceSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscriber_type")
    private SubscriberType subscriberType;

    @Column(name = "subscriber_id")
    private String subscriberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProcessInstanceSubscriptionStatus status;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "notify_all")
    private Boolean notifyAll;

    @Column(name = "notify_tasks")
    private Boolean notifyTasks;

    @Column(name = "notify_attachments")
    private Boolean notifyAttachments;

    @Column(name = "notify_notes")
    private Boolean notifyNotes;

    @Column(name = "notify_chats")
    private Boolean notifyChats;

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

    public ProcessInstanceSubscription id(Long id) {
        this.id = id;
        return this;
    }

    public SubscriberType getSubscriberType() {
        return this.subscriberType;
    }

    public ProcessInstanceSubscription subscriberType(SubscriberType subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public void setSubscriberType(SubscriberType subscriberType) {
        this.subscriberType = subscriberType;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public ProcessInstanceSubscription subscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ProcessInstanceSubscriptionStatus getStatus() {
        return this.status;
    }

    public ProcessInstanceSubscription status(ProcessInstanceSubscriptionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ProcessInstanceSubscriptionStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public ProcessInstanceSubscription date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getNotifyAll() {
        return this.notifyAll;
    }

    public ProcessInstanceSubscription notifyAll(Boolean notifyAll) {
        this.notifyAll = notifyAll;
        return this;
    }

    public void setNotifyAll(Boolean notifyAll) {
        this.notifyAll = notifyAll;
    }

    public Boolean getNotifyTasks() {
        return this.notifyTasks;
    }

    public ProcessInstanceSubscription notifyTasks(Boolean notifyTasks) {
        this.notifyTasks = notifyTasks;
        return this;
    }

    public void setNotifyTasks(Boolean notifyTasks) {
        this.notifyTasks = notifyTasks;
    }

    public Boolean getNotifyAttachments() {
        return this.notifyAttachments;
    }

    public ProcessInstanceSubscription notifyAttachments(Boolean notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
        return this;
    }

    public void setNotifyAttachments(Boolean notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
    }

    public Boolean getNotifyNotes() {
        return this.notifyNotes;
    }

    public ProcessInstanceSubscription notifyNotes(Boolean notifyNotes) {
        this.notifyNotes = notifyNotes;
        return this;
    }

    public void setNotifyNotes(Boolean notifyNotes) {
        this.notifyNotes = notifyNotes;
    }

    public Boolean getNotifyChats() {
        return this.notifyChats;
    }

    public ProcessInstanceSubscription notifyChats(Boolean notifyChats) {
        this.notifyChats = notifyChats;
        return this;
    }

    public void setNotifyChats(Boolean notifyChats) {
        this.notifyChats = notifyChats;
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
        if (!(o instanceof ProcessInstanceSubscription)) {
            return false;
        }
        return id != null && id.equals(((ProcessInstanceSubscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceSubscription{" +
            "id=" + getId() +
            ", subscriberType='" + getSubscriberType() + "'" +
            ", subscriberId='" + getSubscriberId() + "'" +
            ", status='" + getStatus() + "'" +
            ", date='" + getDate() + "'" +
            ", notifyAll='" + getNotifyAll() + "'" +
            ", notifyTasks='" + getNotifyTasks() + "'" +
            ", notifyAttachments='" + getNotifyAttachments() + "'" +
            ", notifyNotes='" + getNotifyNotes() + "'" +
            ", notifyChats='" + getNotifyChats() + "'" +
            "}";
    }
}
