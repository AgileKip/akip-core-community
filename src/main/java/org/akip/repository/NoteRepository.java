package org.akip.repository;

import org.akip.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Note entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	@Query("select ne.note from NoteEntity ne where ne.entityName = ?1 and ne.entityId = ?2 order by ne.note.date asc")
	List<Note> findByEntityNameAndEntityId(String entityName, Long entityId);

	@Query("select ne.note from NoteEntity ne where ne.entityName = ?1 and ne.entityId = ?2 and ne.note.type in ?3 order by ne.note.date asc ")
	List<Note> findByEntityNameAndEntityIdAndTypeIn(String entityName, Long entityId, List<String> types);

	@Modifying
	@Query("update Note set status = ?1 where id = ?2")
    int updateStatusById(String status, Long id);
}
