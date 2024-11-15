package org.akip.service;

import org.akip.domain.AkipEmailConnectorConfig;
import org.akip.repository.AkipEmailConnectorConfigRepository;
import org.akip.service.dto.AkipEmailConnectorConfigDTO;
import org.akip.service.mapper.AkipEmailConnectorConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AkipEmailConnectorConfig}.
 */
@Service
@Transactional
public class AkipEmailConnectorConfigService {

    private final Logger log = LoggerFactory.getLogger(AkipEmailConnectorConfigService.class);

    private final AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository;

    private final AkipEmailConnectorConfigMapper akipEmailConnectorConfigMapper;

    public AkipEmailConnectorConfigService(AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository, AkipEmailConnectorConfigMapper akipEmailConnectorConfigMapper) {
        this.akipEmailConnectorConfigRepository = akipEmailConnectorConfigRepository;
        this.akipEmailConnectorConfigMapper = akipEmailConnectorConfigMapper;
    }


    public AkipEmailConnectorConfigDTO save(AkipEmailConnectorConfigDTO emailActionConfigDTO) {
        log.debug("Request to save EmailActionConfig : {}", emailActionConfigDTO);
        AkipEmailConnectorConfig emailConnectorConfig = akipEmailConnectorConfigMapper.toEntity(emailActionConfigDTO);
        emailConnectorConfig = akipEmailConnectorConfigRepository.save(emailConnectorConfig);
        return akipEmailConnectorConfigMapper.toDto(emailConnectorConfig);
    }

    @Transactional(readOnly = true)
    public Optional<AkipEmailConnectorConfigDTO> findOne(Long id) {
        log.debug("Request to get AkipEmailConnectorConfigDTO : {}", id);
        return akipEmailConnectorConfigRepository.findById(id).map(akipEmailConnectorConfigMapper::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete EmailActionConfig : {}", id);
        akipEmailConnectorConfigRepository.deleteById(id);
    }
}
