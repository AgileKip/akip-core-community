package org.akip.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.Note} entity.
 */
public class NoteDTO implements Serializable {

    private Long id;

    private String author;

    private String text;

    private String type;

    private String status;

    private LocalDateTime date;

    private List<NoteEntityDTO> otherEntities = new ArrayList<>();

    private String entityName;

    private Long entityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<NoteEntityDTO> getOtherEntities() {
        return otherEntities;
    }

    public void setOtherEntities(List<NoteEntityDTO> otherEntities) {
        this.otherEntities = otherEntities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NoteDTO anexoDTO = (NoteDTO) o;
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
        return "NoteDTO{" +
            "id=" + getId() +
            ", author='" + getAuthor() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

}
