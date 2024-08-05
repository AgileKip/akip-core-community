package org.akip.repository;

import org.akip.domain.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the NoteEntity entity.
 */
@Repository
public interface NoteEntityRepository extends JpaRepository<NoteEntity, Long> {

	Optional<NoteEntity> findByEntityNameAndEntityIdAndNoteId(String entityName, Long entityId, Long noteId);

	List<NoteEntity> findByNoteIdAndEntityName(Long noteId, String entityName);

	void deleteByNoteId(Long noteId);

}
