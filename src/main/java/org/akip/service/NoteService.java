package org.akip.service;

import org.akip.domain.Note;
import org.akip.domain.NoteEntity;
import org.akip.publisher.ProcessInstanceEventPublisher;
import org.akip.repository.NoteEntityRepository;
import org.akip.repository.NoteRepository;
import org.akip.service.dto.NoteDTO;
import org.akip.service.mapper.NoteMapper;
import org.akip.validator.INoteValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Note}.
 */
@Service
@Transactional
public class NoteService {

    private final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final static String STATUS_NOTE_CLOSED = "CLOSED";

    private final NoteRepository noteRepository;

    private final NoteEntityRepository noteEntityRepository;

    private final NoteMapper noteMapper;

    private List<INoteValidator> noteValidators = new ArrayList<>();

	private final ProcessInstanceEventPublisher processInstanceEventPublisher;


	public NoteService(NoteRepository noteRepository, NoteEntityRepository noteEntityRepository, NoteMapper noteMapper, ProcessInstanceEventPublisher processInstanceEventPublisher) {
		this.noteRepository = noteRepository;
		this.noteEntityRepository = noteEntityRepository;
		this.noteMapper = noteMapper;
        this.processInstanceEventPublisher = processInstanceEventPublisher;
    }

	public NoteDTO save(NoteDTO noteDTO) {
		if (noteDTO.getId() == null) {
			return create(noteDTO);
		}
		return update(noteDTO);
	}

	public NoteDTO create(NoteDTO noteDTO) {
		log.debug("Request to create Note : {}", noteDTO);
		Note note = noteRepository.save(noteMapper.toEntity(noteDTO));
		linkNoteToEntities(note, noteDTO);
		processInstanceEventPublisher.publishEventAddedNote(noteMapper.toDto(note));
		return noteMapper.toDto(note);
	}

	public NoteDTO update(NoteDTO noteDTO) {
		log.debug("Request to update Note : {}", noteDTO);
		Note note = noteRepository.save(noteMapper.toEntity(noteDTO));
		linkNoteToEntities(note, noteDTO);
		processInstanceEventPublisher.publishEventChangedNote(noteDTO);
		return noteMapper.toDto(note);
	}

	private void linkNoteToEntities(Note note, NoteDTO noteDTO) {
		linkNoteToEntity(note, noteDTO.getEntityName(), noteDTO.getEntityId());
		noteDTO.getOtherEntities().forEach(noteEntityDTO -> {
			linkNoteToEntity(note, noteEntityDTO.getEntityName(), noteEntityDTO.getEntityId());
		});
	}

	private void linkNoteToEntity(Note note, String entityName, Long entityId) {
		validate(entityName, entityId);
		if (noteEntityRepository.findByEntityNameAndEntityIdAndNoteId(entityName, entityId, note.getId()).isPresent()) {
			//The note is already linked to the entity (name/id)
			return;
		}

		NoteEntity noteEntity = new NoteEntity();
		noteEntity.setNote(note);
		noteEntity.setEntityName(entityName);
		noteEntity.setEntityId(entityId);
		noteEntityRepository.save(noteEntity);
	}

	public NoteDTO get(Long noteId) {
		return noteMapper.toDto(noteRepository.getOne(noteId));
	}

	public void delete(Long noteId) {
		log.debug("Request to delete Note : {}", noteId);
		NoteDTO noteDTO = noteMapper.toDto(noteRepository.getOne(noteId));
		noteEntityRepository.deleteByNoteId(noteId);
		processInstanceEventPublisher.publishEventRemovedNote(noteDTO);
		noteRepository.deleteById(noteId);
	}
    
    @Transactional(readOnly = true)
	public List<NoteDTO> findByEntityNameAndEntityId(String entityName, Long entityId) {
    	log.debug("Request to findByEntityNameAndEntitiesIds NoteEntity : entityName={}; entityId={}", entityName, entityId);
        return noteRepository.findByEntityNameAndEntityId(entityName, entityId)
        		.stream()
        		.map(noteMapper::toDto)
        		.collect(Collectors.toCollection(LinkedList::new));
	}

	@Transactional(readOnly = true)
	public List<NoteDTO> findByEntityNameAndEntityIdAndTypes(String entityName, Long entityId, List<String> types) {
		log.debug("Request to findByEntityNameAndEntityIdAndTypes: entityName={}; entityId={}; types{}", entityName, entityId, types);
		return noteRepository.findByEntityNameAndEntityIdAndTypeIn(entityName, entityId, types)
				.stream()
				.map(noteMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	public void registerValidator(INoteValidator noteValidator) {
		this.noteValidators.add(noteValidator);
	}

	private void validate(String entityName, Long entityId) {
		this.noteValidators
				.stream()
				.filter(noteValidator -> noteValidator.getEntityName().equals(entityName))
				.forEach(noteValidator -> noteValidator.validate(entityId));
	}

    public void closeNotesAssociatedToEntity(String entityName, Long entityId) {
		noteRepository.findByEntityNameAndEntityId(entityName, entityId).forEach(note -> {
			noteRepository.updateStatusById(STATUS_NOTE_CLOSED, note.getId());
		});
    }
}
