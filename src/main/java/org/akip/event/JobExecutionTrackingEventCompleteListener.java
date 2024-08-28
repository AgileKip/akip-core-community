package org.akip.event;

import org.akip.repository.JobExecutionTrackingRepository;
import org.akip.service.dto.JobExecutionTrackingDTO;
import org.akip.service.mapper.JobExecutionTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JobExecutionTrackingEventCompleteListener {

    private Logger log = LoggerFactory.getLogger(JobExecutionTrackingEventCompleteListener.class);

    private final JobExecutionTrackingRepository jobExecutionTrackingRepository;
    private final JobExecutionTrackingMapper jobExecutionTrackingMapper;

    public JobExecutionTrackingEventCompleteListener(
        JobExecutionTrackingRepository jobExecutionTrackingRepository,
        JobExecutionTrackingMapper jobExecutionTrackingMapper
    ) {
        super();
        this.jobExecutionTrackingRepository = jobExecutionTrackingRepository;
        this.jobExecutionTrackingMapper = jobExecutionTrackingMapper;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(JobExecutionTrackingEventComplete event) {
        log.debug("Executing JobExecutionTrackingEventCompleteListener...");
        JobExecutionTrackingEventDTO jobExecutionTrackingEvent = (JobExecutionTrackingEventDTO) event.getSource();
        JobExecutionTrackingDTO jobExecutionTracking = jobExecutionTrackingRepository
            .findByIdentifier(jobExecutionTrackingEvent.getIdentifier())
            .map(jobExecutionTrackingMapper::toDto)
            .get();
        jobExecutionTracking.setStatus(jobExecutionTrackingEvent.getStatus());
        jobExecutionTracking.setSummary(jobExecutionTrackingEvent.getSummary());
        jobExecutionTrackingRepository.save(jobExecutionTrackingMapper.toEntity(jobExecutionTracking));
    }
}
