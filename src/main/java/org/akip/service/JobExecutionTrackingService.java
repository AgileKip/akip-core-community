package org.akip.service;

import org.akip.domain.JobExecutionTracking;
import org.akip.repository.JobExecutionTrackingRepository;
import org.akip.service.dto.JobExecutionTrackingDTO;
import org.akip.service.mapper.JobExecutionTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link JobExecutionTracking}.
 */
@Service
@Transactional
public class JobExecutionTrackingService {

    private final Logger log = LoggerFactory.getLogger(JobExecutionTrackingService.class);

    private final JobExecutionTrackingRepository jobExecutionTrackingRepository;

    private final JobExecutionTrackingMapper jobExecutionTrackingMapper;

    public JobExecutionTrackingService(
        JobExecutionTrackingRepository jobExecutionTrackingRepository,
        JobExecutionTrackingMapper jobExecutionTrackingMapper
    ) {
        this.jobExecutionTrackingRepository = jobExecutionTrackingRepository;
        this.jobExecutionTrackingMapper = jobExecutionTrackingMapper;
    }

    /**
     * Save a jobExecutionTracking.
     *
     * @param jobExecutionTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    public JobExecutionTrackingDTO save(JobExecutionTrackingDTO jobExecutionTrackingDTO) {
        log.debug("Request to save JobExecutionTracking : {}", jobExecutionTrackingDTO);
        JobExecutionTracking jobExecutionTracking = jobExecutionTrackingMapper.toEntity(jobExecutionTrackingDTO);
        jobExecutionTracking = jobExecutionTrackingRepository.save(jobExecutionTracking);
        return jobExecutionTrackingMapper.toDto(jobExecutionTracking);
    }

    /**
     * Partially update a jobExecutionTracking.
     *
     * @param jobExecutionTrackingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobExecutionTrackingDTO> partialUpdate(JobExecutionTrackingDTO jobExecutionTrackingDTO) {
        log.debug("Request to partially update JobExecutionTracking : {}", jobExecutionTrackingDTO);

        return jobExecutionTrackingRepository
            .findById(jobExecutionTrackingDTO.getId())
            .map(
                existingJobExecutionTracking -> {
                    jobExecutionTrackingMapper.partialUpdate(existingJobExecutionTracking, jobExecutionTrackingDTO);
                    return existingJobExecutionTracking;
                }
            )
            .map(jobExecutionTrackingRepository::save)
            .map(jobExecutionTrackingMapper::toDto);
    }

    /**
     * Get all the jobExecutionTrackings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<JobExecutionTrackingDTO> findAll() {
        log.debug("Request to get all JobExecutionTrackings");
        return jobExecutionTrackingRepository
            .findAll()
            .stream()
            .map(jobExecutionTrackingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jobExecutionTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobExecutionTrackingDTO> findOne(Long id) {
        log.debug("Request to get JobExecutionTracking : {}", id);
        return jobExecutionTrackingRepository.findById(id).map(jobExecutionTrackingMapper::toDto);
    }

    /**
     * Delete the jobExecutionTracking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JobExecutionTracking : {}", id);
        jobExecutionTrackingRepository.deleteById(id);
    }
}
