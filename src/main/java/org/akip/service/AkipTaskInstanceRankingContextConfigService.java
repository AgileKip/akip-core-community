package org.akip.service;

import org.akip.domain.AkipTaskInstanceRankingContextConfig;
import org.akip.repository.AkipTaskInstanceRankingContextConfigRepository;
import org.akip.service.dto.AkipTaskInstanceRankingContextConfigDTO;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.mapper.AkipTaskInstanceRankingContextConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AkipTaskInstanceRankingContextConfig}.
 */
@Service
@Transactional
public class AkipTaskInstanceRankingContextConfigService {

    private final Logger log = LoggerFactory.getLogger(AkipTaskInstanceRankingContextConfigService.class);

    private final AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository;

    private final AkipTaskInstanceRankingContextConfigMapper akipTaskInstanceRankingContextConfigMapper;

    private final ProcessDefinitionService processDefinitionService;

    public AkipTaskInstanceRankingContextConfigService(
        AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository,
        AkipTaskInstanceRankingContextConfigMapper akipTaskInstanceRankingContextConfigMapper,
        ProcessDefinitionService processDefinitionService) {
        this.akipTaskInstanceRankingContextConfigRepository = akipTaskInstanceRankingContextConfigRepository;
        this.akipTaskInstanceRankingContextConfigMapper = akipTaskInstanceRankingContextConfigMapper;
        this.processDefinitionService = processDefinitionService;
    }

    /**
     * Save a akipTaskInstanceRankingContextConfig.
     *
     * @param akipTaskInstanceRankingContextConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public AkipTaskInstanceRankingContextConfigDTO save(AkipTaskInstanceRankingContextConfigDTO akipTaskInstanceRankingContextConfigDTO) {
        log.debug("Request to save AkipTaskInstanceRankingContextConfig : {}", akipTaskInstanceRankingContextConfigDTO);
        AkipTaskInstanceRankingContextConfig akipTaskInstanceRankingContextConfig = akipTaskInstanceRankingContextConfigMapper.toEntity(
            akipTaskInstanceRankingContextConfigDTO
        );
        akipTaskInstanceRankingContextConfig = akipTaskInstanceRankingContextConfigRepository.save(akipTaskInstanceRankingContextConfig);
        return akipTaskInstanceRankingContextConfigMapper.toDto(akipTaskInstanceRankingContextConfig);
    }

    /**
     * Get all the akipTaskInstanceRankingContextConfigs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AkipTaskInstanceRankingContextConfigDTO> findAll() {
        log.debug("Request to get all AkipTaskInstanceRankingContextConfigs");

        List<ProcessDefinitionDTO> processDefinitions = processDefinitionService.findAll();

        List<AkipTaskInstanceRankingContextConfigDTO> configs = processDefinitions.stream()
                .map(processDefinition -> {
                    AkipTaskInstanceRankingContextConfigDTO config = new AkipTaskInstanceRankingContextConfigDTO();
                    config.setProcessDefinition(processDefinition);
                    return config;
                })
                .collect(Collectors.toCollection(LinkedList::new));

        List<AkipTaskInstanceRankingContextConfigDTO> existingConfigs = akipTaskInstanceRankingContextConfigRepository
            .findAll()
            .stream()
            .map(akipTaskInstanceRankingContextConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

        configs.forEach(config -> {
            existingConfigs
                    .stream()
                    .filter(existingConfig -> config.getProcessDefinition().getId().equals(existingConfig.getProcessDefinition().getId()))
                    .findFirst().ifPresent(akipTaskInstanceRankingContextConfigDTO -> config.setId(akipTaskInstanceRankingContextConfigDTO.getId()));
        });

        return configs;
    }

    /**
     * Get one akipTaskInstanceRankingContextConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AkipTaskInstanceRankingContextConfigDTO> findOne(Long id) {
        log.debug("Request to get AkipTaskInstanceRankingContextConfig : {}", id);
        return akipTaskInstanceRankingContextConfigRepository.findById(id).map(akipTaskInstanceRankingContextConfigMapper::toDto);
    }

    /**
     * Delete the akipTaskInstanceRankingContextConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AkipTaskInstanceRankingContextConfig : {}", id);
        akipTaskInstanceRankingContextConfigRepository.deleteById(id);
    }
}
