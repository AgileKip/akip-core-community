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
public class JobExecutionTrackingEventStartListener {

    private Logger log = LoggerFactory.getLogger(JobExecutionTrackingEventStartListener.class);

    private final JobExecutionTrackingRepository jobExecutionTrackingRepository;
    private final JobExecutionTrackingMapper jobExecutionTrackingMapper;

    public JobExecutionTrackingEventStartListener(
        JobExecutionTrackingRepository jobExecutionTrackingRepository,
        JobExecutionTrackingMapper jobExecutionTrackingMapper
    ) {
        super();
        this.jobExecutionTrackingRepository = jobExecutionTrackingRepository;
        this.jobExecutionTrackingMapper = jobExecutionTrackingMapper;
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(JobExecutionTrackingEventStart event) {
        log.debug("Executing JobExecutionTrackingEventStartListener...");
        JobExecutionTrackingEventDTO jobExecutionTrackingEvent = (JobExecutionTrackingEventDTO) event.getSource();
        jobExecutionTrackingRepository.save(
            jobExecutionTrackingMapper.toEntity(
                new JobExecutionTrackingDTO(jobExecutionTrackingEvent.getIdentifier(), jobExecutionTrackingEvent.getCronName())
            )
        );
    }
}
