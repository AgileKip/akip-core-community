package org.akip.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.NoteEntity} entity.
 */
public class NoteEntityDTO implements Serializable {

    private Long id;

    private Long entityId;

    private String entityName;

    private Long noteId;
    
    private NoteDTO note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NoteEntityDTO anexoEntityDTO = (NoteEntityDTO) o;
        if (anexoEntityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoEntityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NoteEntityDTO{" +
            "id=" + getId() +
            ", entityId=" + getEntityId() +
            ", entityName='" + getEntityName() + "'" +
            ", noteId=" + getNoteId() +
            "}";
    }

}
