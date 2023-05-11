package org.akip.repository;

import org.akip.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Attachment entity.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	@Query("select ae.attachment from AttachmentEntity ae where ae.entityName = ?1 and ae.entityId = ?2")
	List<Attachment> findByEntityNameAndEntityId(String entityName, Long entityId);

	@Query("select ae.attachment from AttachmentEntity ae where ae.entityName = ?1 and ae.entityId = ?2 and ae.attachment.type in ?3")
	List<Attachment> findByEntityNameAndEntityIdAndTypeIn(String entityName, Long entityId, List<String> types);

}
