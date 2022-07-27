package org.akip.web.rest;

import org.akip.service.NoteService;
import org.akip.service.dto.NoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.Note}.
 */
@RestController
@RequestMapping("/api")
public class NoteController {

    private final Logger log = LoggerFactory.getLogger(NoteController.class);

    private final String ENTITY_NAME = "note";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public ResponseEntity<Void> create(@RequestBody NoteDTO noteDTO) throws URISyntaxException {
        log.debug("REST request to save note: {}", noteDTO);
        noteService.create(noteDTO);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "")).build();
    }

    @PutMapping("/notes")
    public ResponseEntity<Void> update(@RequestBody NoteDTO noteDTO) throws URISyntaxException {
        log.debug("REST request to update note: {}", noteDTO);
        noteService.save(noteDTO);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "")).build();
    }

    @GetMapping("/notes/{noteId}")
    public ResponseEntity<NoteDTO> get(@PathVariable Long noteId) {
        log.debug("REST request to get note: {}", noteId);
        NoteDTO note = noteService.get(noteId);
        return ResponseEntity.ok().body(note);
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> delete(@PathVariable Long noteId) {
        log.debug("REST request to get note: {}", noteId);
        noteService.delete(noteId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, noteId.toString())).build();
    }

    @GetMapping("/{entityName}/{entityId}/notes")
    public List<NoteDTO> getNotesByEntityNameAndEntityId(@PathVariable String entityName, @PathVariable Long entityId) { log.debug("REST request to getNotesByEntityNameAndEntitiesIds: entityName={}, entitiesIds={}", entityName, entityId);
        return noteService.findByEntityNameAndEntityId(entityName, entityId);
    }

    @GetMapping("/{entityName}/{entityId}/{noteTypes}/notes")
    public List<NoteDTO> getNotesByEntityNameAndEntityIdAndTypes(@PathVariable String entityName, @PathVariable Long entityId, @PathVariable List<String> noteTypes) {
        log.debug("REST request to getNotesByEntityIdAndEntityNameAndTypes: entityName={}, entityId={}, types={}", entityName, entityId, noteTypes);
        return noteService.findByEntityNameAndEntityIdAndTypes(entityName, entityId, noteTypes);
    }

}
