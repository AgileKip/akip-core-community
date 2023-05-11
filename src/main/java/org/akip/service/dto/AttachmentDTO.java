package org.akip.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.Attachment} entity.
 */
public class AttachmentDTO implements Serializable {

    private Long id;

    private String name;

    private String status;

    private String type;

    private LocalDateTime uploadedDate;

    private String uploadedBy;

    private byte[] bytes;

    private String bytesContentType;

    private List<AttachmentEntityDTO> otherEntities = new ArrayList<>();

    private String entityName;

    private Long entityId;

    private String originalContext;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBytesContentType() {
        return bytesContentType;
    }

    public void setBytesContentType(String bytesContentType) {
        this.bytesContentType = bytesContentType;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOriginalContext() {
        return originalContext;
    }

    public void setOriginalContext(String originalContext) {
        this.originalContext = originalContext;
    }

    public List<AttachmentEntityDTO> getOtherEntities() {
        return otherEntities;
    }

    public void setOtherEntities(List<AttachmentEntityDTO> otherEntities) {
        this.otherEntities = otherEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentDTO anexoDTO = (AttachmentDTO) o;
        if (anexoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return (
            "AttachmentDTO{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", type='" +
            getType() +
            "'" +
            ", status='" +
            getStatus() +
            "'" +
            "}"
        );
    }
}
