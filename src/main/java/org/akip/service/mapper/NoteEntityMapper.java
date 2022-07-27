package org.akip.service.mapper;


import org.akip.domain.NoteEntity;
import org.akip.service.dto.NoteEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link NoteEntity} and its DTO {@link NoteEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {NoteMapper.class})
public interface NoteEntityMapper extends EntityMapper<NoteEntityDTO, NoteEntity> {

    @Mapping(source = "note.id", target = "noteId")
    NoteEntityDTO toDto(NoteEntity noteEntity);

    @Mapping(source = "noteId", target = "note")
    NoteEntity toEntity(NoteEntityDTO noteEntityDTO);

    default NoteEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(id);
        return noteEntity;
    }
}
