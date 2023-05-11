package org.akip.service.mapper;

import org.akip.domain.AttachmentEntity;
import org.akip.service.dto.AttachmentEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link org.akip.domain.AttachmentEntity} and its DTO {@link AttachmentEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttachmentMapper.class})
public interface AttachmentEntityMapper extends EntityMapper<AttachmentEntityDTO, AttachmentEntity> {

    @Mapping(source = "attachment.id", target = "attachmentId")
    AttachmentEntityDTO toDto(AttachmentEntity attachmentEntity);

    @Mapping(source = "attachmentId", target = "attachment")
    AttachmentEntity toEntity(AttachmentEntityDTO attachmentEntityDTO);

    default AttachmentEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setId(id);
        return attachmentEntity;
    }
}
