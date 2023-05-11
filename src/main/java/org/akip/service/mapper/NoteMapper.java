package org.akip.service.mapper;

import org.akip.domain.Note;
import org.akip.service.dto.NoteDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Note} and its DTO {@link NoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoteMapper extends EntityMapper<NoteDTO, Note> {

    default Note fromId(Long id) {
        if (id == null) {
            return null;
        }
        Note note = new Note();
        note.setId(id);
        return note;
    }

}
