package org.akip.service.mapper;

import org.akip.domain.Attachment;
import org.akip.service.dto.AttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link org.akip.domain.Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {

    @Mapping(target = "bytes", ignore = true)
    @Mapping(target = "entityName", ignore = true)
    @Mapping(target = "entityId", ignore = true)
    AttachmentDTO toDto(Attachment attachment);

    Attachment toEntity(AttachmentDTO attachmentDTO);

    default Attachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setId(id);
        return attachment;
    }

}
