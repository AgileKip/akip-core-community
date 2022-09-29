package org.akip.service;

import org.akip.domain.KipApp;
import org.akip.repository.KipAppRepository;
import org.akip.service.dto.KipAppDTO;
import org.akip.service.mapper.KipAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link KipApp}.
 */
@Service
@Transactional
public class KipAppService {

    private final Logger log = LoggerFactory.getLogger(KipAppService.class);

    private final KipAppRepository kipAppRepository;

    private final KipAppMapper kipAppMapper;

    public KipAppService(KipAppRepository kipAppRepository, KipAppMapper kipAppMapper) {
        this.kipAppRepository = kipAppRepository;
        this.kipAppMapper = kipAppMapper;
    }

    /**
     * Save a kipApp.
     *
     * @param kipAppDTO the entity to save.
     * @return the persisted entity.
     */
    public KipAppDTO save(KipAppDTO kipAppDTO) {
        log.debug("Request to save KipApp : {}", kipAppDTO);
        KipApp kipApp = kipAppMapper.toEntity(kipAppDTO);
        kipApp = kipAppRepository.save(kipApp);
        return kipAppMapper.toDto(kipApp);
    }

    /**
     * Partially update a kipApp.
     *
     * @param kipAppDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<KipAppDTO> partialUpdate(KipAppDTO kipAppDTO) {
        log.debug("Request to partially update KipApp : {}", kipAppDTO);

        return kipAppRepository
            .findById(kipAppDTO.getId())
            .map(
                existingKipApp -> {
                    kipAppMapper.partialUpdate(existingKipApp, kipAppDTO);
                    return existingKipApp;
                }
            )
            .map(kipAppRepository::save)
            .map(kipAppMapper::toDto);
    }

    /**
     * Get all the kipApps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<KipAppDTO> findAll() {
        log.debug("Request to get all KipApps");
        return kipAppRepository.findAll().stream().map(kipAppMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one kipApp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<KipAppDTO> findOne(Long id) {
        log.debug("Request to get KipApp : {}", id);
        return kipAppRepository.findById(id).map(kipAppMapper::toDto);
    }

    /**
     * Delete the kipApp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete KipApp : {}", id);
        kipAppRepository.deleteById(id);
    }

    public void activate(Long id) {
    }

    public void inactivate(Long id) {
    }
}
