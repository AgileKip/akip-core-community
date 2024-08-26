package org.akip.domain;

import org.akip.domain.enumeration.ActiveInactiveStatus;
import org.akip.domain.enumeration.SubscriberType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProcessDefinitionSubscription.
 */
@Entity
@Table(name = "process_definition_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessDefinitionSubscription implements Serializable {

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
    private ActiveInactiveStatus status;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;

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

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    @ManyToOne
    private ProcessDefinition processDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessDefinitionSubscription id(Long id) {
        this.id = id;
        return this;
    }

    public SubscriberType getSubscriberType() {
        return this.subscriberType;
    }

    public ProcessDefinitionSubscription subscriberType(SubscriberType subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public void setSubscriberType(SubscriberType subscriberType) {
        this.subscriberType = subscriberType;
    }

    public String getSubscriberId() {
        return this.subscriberId;
    }

    public ProcessDefinitionSubscription subscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ActiveInactiveStatus getStatus() {
        return this.status;
    }

    public ProcessDefinitionSubscription status(ActiveInactiveStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

    public LocalDate getSubscriptionDate() {
        return this.subscriptionDate;
    }

    public ProcessDefinitionSubscription date(LocalDate date) {
        this.subscriptionDate = date;
        return this;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Boolean getNotifyAll() {
        return this.notifyAll;
    }

    public ProcessDefinitionSubscription notifyAll(Boolean notifyAll) {
        this.notifyAll = notifyAll;
        return this;
    }

    public void setNotifyAll(Boolean notifyAll) {
        this.notifyAll = notifyAll;
    }

    public Boolean getNotifyTasks() {
        return this.notifyTasks;
    }

    public ProcessDefinitionSubscription notifyTasks(Boolean notifyTasks) {
        this.notifyTasks = notifyTasks;
        return this;
    }

    public void setNotifyTasks(Boolean notifyTasks) {
        this.notifyTasks = notifyTasks;
    }

    public Boolean getNotifyAttachments() {
        return this.notifyAttachments;
    }

    public ProcessDefinitionSubscription notifyAttachments(Boolean notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
        return this;
    }

    public void setNotifyAttachments(Boolean notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
    }

    public Boolean getNotifyNotes() {
        return this.notifyNotes;
    }

    public ProcessDefinitionSubscription notifyNotes(Boolean notifyNotes) {
        this.notifyNotes = notifyNotes;
        return this;
    }

    public void setNotifyNotes(Boolean notifyNotes) {
        this.notifyNotes = notifyNotes;
    }

    public Boolean getNotifyChats() {
        return this.notifyChats;
    }

    public ProcessDefinitionSubscription notifyChats(Boolean notifyChats) {
        this.notifyChats = notifyChats;
        return this;
    }

    public void setNotifyChats(Boolean notifyChats) {
        this.notifyChats = notifyChats;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinitionSubscription)) {
            return false;
        }
        return id != null && id.equals(((ProcessDefinitionSubscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDefinitionSubscription{" +
            "id=" + getId() +
            ", subscriberId='" + getSubscriberId() + "'" +
            "}";
    }
}
