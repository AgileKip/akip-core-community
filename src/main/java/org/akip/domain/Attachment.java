package org.akip.domain;

import org.akip.security.SecurityUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * An Attachment
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "uploaded_date")
    private LocalDateTime uploadedDate;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Column(name = "bytes_content_type")
    private String bytesContentType;

    @Column(name = "original_context")
    private String originalContext;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getBytesContentType() {
        return bytesContentType;
    }

    public void setBytesContentType(String bytesContentType) {
        this.bytesContentType = bytesContentType;
    }

    public String getOriginalContext() {
        return originalContext;
    }

    public void setOriginalContext(String taskDefinitionKey) {
        this.originalContext = taskDefinitionKey;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.setUploadedDate(LocalDateTime.now());
        if (SecurityUtils.getCurrentUserLogin().isPresent()) {
            this.setUploadedBy(SecurityUtils.getCurrentUserLogin().get());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return (
            "Attachment{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", status='" +
            getStatus() +
            "'" +
            ", type='" +
            getType() +
            "'" +
            "}"
        );
    }
}
