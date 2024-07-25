package org.akip.repository;

import org.akip.domain.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AttachmentEntity entity.
 */
@Repository
public interface AttachmentEntityRepository extends JpaRepository<AttachmentEntity, Long> {

	Optional<AttachmentEntity> findByEntityNameAndEntityIdAndAttachmentId(String entityName, Long entityId, Long attachmentId);

	List<AttachmentEntity> findAttachmentEntityByAttachment_Id(Long attachmentId);

	void deleteByAttachmentId(Long attachmentId);

}
