package org.akip.service;

import org.akip.domain.AkipPromptConfiguration;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.AkipPromptConfigurationRepository;
import org.akip.service.dto.AkipPromptConfigurationDTO;
import org.akip.service.mapper.AkipPromptConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AkipPromptConfiguration}.
 */
@Service
@Transactional
public class AkipPromptConfigurationService {

    private final Logger log = LoggerFactory.getLogger(AkipPromptConfigurationService.class);

    private final AkipPromptConfigurationRepository akipPromptConfigurationRepository;

    private final AkipPromptConfigurationMapper akipPromptConfigurationMapper;

    public AkipPromptConfigurationService(
        AkipPromptConfigurationRepository akipPromptConfigurationRepository,
        AkipPromptConfigurationMapper akipPromptConfigurationMapper
    ) {
        this.akipPromptConfigurationRepository = akipPromptConfigurationRepository;
        this.akipPromptConfigurationMapper = akipPromptConfigurationMapper;
    }

    /**
     * Save a akipPromptConfiguration.
     *
     * @param akipPromptConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public AkipPromptConfigurationDTO save(AkipPromptConfigurationDTO akipPromptConfigurationDTO) {
        log.debug("Request to save AkipPromptConfiguration : {}", akipPromptConfigurationDTO);
        AkipPromptConfiguration akipPromptConfiguration = akipPromptConfigurationMapper.toEntity(akipPromptConfigurationDTO);
        akipPromptConfiguration = akipPromptConfigurationRepository.save(akipPromptConfiguration);
        return akipPromptConfigurationMapper.toDto(akipPromptConfiguration);
    }

    /**
     * Partially update a akipPromptConfiguration.
     *
     * @param akipPromptConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AkipPromptConfigurationDTO> partialUpdate(AkipPromptConfigurationDTO akipPromptConfigurationDTO) {
        log.debug("Request to partially update AkipPromptConfiguration : {}", akipPromptConfigurationDTO);

        return akipPromptConfigurationRepository
            .findById(akipPromptConfigurationDTO.getId())
            .map(
                existingAkipPromptConfiguration -> {
                    akipPromptConfigurationMapper.partialUpdate(existingAkipPromptConfiguration, akipPromptConfigurationDTO);
                    return existingAkipPromptConfiguration;
                }
            )
            .map(akipPromptConfigurationRepository::save)
            .map(akipPromptConfigurationMapper::toDto);
    }

    /**
     * Get all the akipPromptConfigurations.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipPromptConfigurationDTO> findAll() {
        log.debug("Request to get all AkipPromptConfigurations");
        return akipPromptConfigurationRepository
            .findAll()
            .stream()
            .map(akipPromptConfigurationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one akipPromptConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkipPromptConfigurationDTO> findOne(Long id) {
        log.debug("Request to get AkipPromptConfiguration : {}", id);
        return akipPromptConfigurationRepository.findById(id).map(akipPromptConfigurationMapper::toDto);
    }

    /**
     * Delete the akipPromptConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkipPromptConfiguration : {}", id);
        akipPromptConfigurationRepository.deleteById(id);
    }

    public AkipPromptConfigurationDTO findByName(String name) {
        return akipPromptConfigurationRepository
                .findByName(name)
                .map(akipPromptConfigurationMapper::toDto)
                .orElseThrow(() -> { return new BadRequestErrorException("akip.akipPromptConfiguration.error.notFound", name); });
    }
}
