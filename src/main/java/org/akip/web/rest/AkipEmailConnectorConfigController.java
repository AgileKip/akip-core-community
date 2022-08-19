package org.akip.web.rest;

import org.akip.delegate.executor.AkipEmailConnectorExecutor;
import org.akip.delegate.executor.AkipEmailConnectorMessageDTO;
import org.akip.delegate.executor.AkipEmailConnectorConfigTestRequestDTO;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.AkipEmailConnectorConfigRepository;
import org.akip.service.AkipEmailConnectorConfigService;
import org.akip.service.dto.AkipEmailConnectorConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link AkipEmailConnectorConfigController}.
 */
@RestController
@RequestMapping("/api")
public class AkipEmailConnectorConfigController {

    private static final String ENTITY_NAME = "emailActionConfig";
    private static final String MESSAGE_CONNECTOR_CONFIG_CREATED = "Connector Config Successfully Created";
    private static final String MESSAGE_CONNECTOR_CONFIG_UPDATED = "Connector Config Successfully Updated";
    private static final String MESSAGE_CONNECTOR_CONFIG_REMOVED = "Connector Config Successfully Removed";

    private final Logger log = LoggerFactory.getLogger(AkipEmailConnectorConfigController.class);

    private final AkipEmailConnectorExecutor akipEmailConnectorExecutor;

    private final AkipEmailConnectorConfigService akipEmailConnectorConfigService;

    private final AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository;

    public AkipEmailConnectorConfigController(AkipEmailConnectorExecutor akipEmailConnectorExecutor,
                                              AkipEmailConnectorConfigService akipEmailConnectorConfigService,
                                              AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository) {
        this.akipEmailConnectorExecutor = akipEmailConnectorExecutor;
        this.akipEmailConnectorConfigService = akipEmailConnectorConfigService;
        this.akipEmailConnectorConfigRepository = akipEmailConnectorConfigRepository;
    }

    @GetMapping("/akip-email-connector-configs/{id}")
    public ResponseEntity<AkipEmailConnectorConfigDTO> get(@PathVariable Long id) {
        log.debug("REST request to get AkipEmailConnectorConfig : {}", id);
        Optional<AkipEmailConnectorConfigDTO> emailActionConfigDTO = akipEmailConnectorConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailActionConfigDTO);
    }

    @PostMapping("/akip-email-connector-configs")
    public ResponseEntity<AkipEmailConnectorConfigDTO> create(@RequestBody AkipEmailConnectorConfigDTO akipEmailConnectorConfig) throws URISyntaxException {
        log.debug("REST request to save AkipEmailConnectorConfig : {}", akipEmailConnectorConfig);
        if (akipEmailConnectorConfig.getId() != null) {
            throw new BadRequestErrorException("A new akipEmailConnectorConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipEmailConnectorConfigDTO result = akipEmailConnectorConfigService.save(akipEmailConnectorConfig);
        return ResponseEntity
                .created(new URI("/api/akip-email-connector-configs/" + result.getId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_CONNECTOR_CONFIG_CREATED, result.getId().toString()))
                .body(result);
    }

    @PutMapping("/akip-email-connector-configs/{id}")
    public ResponseEntity<AkipEmailConnectorConfigDTO> update(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody AkipEmailConnectorConfigDTO akipEmailConnectorConfig
    ) throws URISyntaxException {
        log.debug("REST request to update AkipEmailConnectorConfig : {}, {}", id, akipEmailConnectorConfig);
        if (akipEmailConnectorConfig.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipEmailConnectorConfig.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipEmailConnectorConfigRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipEmailConnectorConfigDTO result = akipEmailConnectorConfigService.save(akipEmailConnectorConfig);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_CONNECTOR_CONFIG_UPDATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/akip-email-connector-configs/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete AkipEmailConnectorConfig : {}", id);
        akipEmailConnectorConfigService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_CONNECTOR_CONFIG_REMOVED, id.toString()))
                .build();
    }

    @PostMapping("/akip-email-connector-configs/test")
    public AkipEmailConnectorMessageDTO test(@RequestBody AkipEmailConnectorConfigTestRequestDTO emailConnectorTestRequest) throws URISyntaxException {
        log.debug("REST request to test AkipEmailConnectorConfig : {}", emailConnectorTestRequest);
        try {
            return akipEmailConnectorExecutor.buildMessage(emailConnectorTestRequest);
        } catch (Exception e) {
            throw new BadRequestErrorException(e.toString());
        }
    }
}
