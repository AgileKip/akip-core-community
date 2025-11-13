package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.CamundaJobDTO;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.mapper.CamundaJobsMapper;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.runtime.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/camunda-jobs")
public class CamundaJobsController {

    private final Logger log = LoggerFactory.getLogger(CamundaJobsController.class);

    @Autowired
    ManagementService managementService;

    @Autowired
    CamundaJobsMapper camundaJobsMapper;

    @Autowired
    ProcessInstanceRepository processInstanceRepository;

    @Autowired
    ProcessInstanceMapper processInstanceMapper;

    public CamundaJobsController() {
    }

    @GetMapping("/incidents")
    public List<CamundaJobDTO> getIncidents() {

        log.debug("REST request all Incidents");
        try {
            List<Job> jobs = managementService.createJobQuery().withException().list();
            return jobs.stream().map(camundaJobsMapper::mapToCamundaJobDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao retornar JOBS: " + e.getMessage());
            throw new BadRequestErrorException("akip.incidents.error.get ", "");
        }

    }

    @GetMapping("/process-instance/{id}/incidents")
    public List<CamundaJobDTO> getIncidentsByProcessInstanceId(@PathVariable("id") Long id) {
        ProcessInstanceDTO processInstance = processInstanceRepository.findById(id)
                .map(processInstanceMapper::toDto)
                .orElseThrow();

        log.debug("REST request all Incidents by processInstanceId");
        try {
            List<Job> jobs = managementService.createJobQuery().processInstanceId(processInstance.getCamundaProcessInstanceId()).withException().list();
            return jobs.stream().map(camundaJobsMapper::mapToCamundaJobDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao retornar JOBS: " + e.getMessage());
            throw new BadRequestErrorException("akip.incidents.error.get ", "");
        }

    }

    @GetMapping("/timers")
    public List<CamundaJobDTO> getTimers() {

        log.debug("REST request all Timers");
        try {
            List<Job> jobs = managementService.createJobQuery().list().stream().filter(it -> it.getExceptionMessage() == null).toList();
            return jobs.stream().map(camundaJobsMapper::mapToCamundaJobDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao retornar JOBS: " + e.getMessage());
            throw new BadRequestErrorException("akip.incidents.error.get ", "");
        }

    }

    @GetMapping("/process-instance/{id}/timers")
    public List<CamundaJobDTO> getTimersByProcessInstanceId(@PathVariable("id") Long id) {
        ProcessInstanceDTO processInstance = processInstanceRepository.findById(id)
                .map(processInstanceMapper::toDto)
                .orElseThrow();

        log.debug("REST request all timers by processInstanceId");
        try {
            List<Job> jobs = managementService.createJobQuery().
                    processInstanceId(processInstance.getCamundaProcessInstanceId()).list().stream().filter(it -> it.getExceptionMessage() == null).toList();
            return jobs.stream().map(camundaJobsMapper::mapToCamundaJobDTO).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao retornar JOBS: " + e.getMessage());
            throw new BadRequestErrorException("akip.incidents.error.get ", "");
        }

    }

    @PostMapping("/job/{jobId}/execute")
    public void executeJob(@PathVariable("jobId") String jobId) {
        log.debug("REST request execute a Job Sincronamente");
        try {
            managementService.executeJob(jobId);
        } catch (Exception e) {
            log.error("Erro ao executar JOB: " + e.getMessage());
            throw new BadRequestErrorException("akip.camundaJobs.error.execute ", jobId, e.getMessage());
        }
    }

}
