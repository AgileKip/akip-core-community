package org.akip.service;

import org.akip.camunda.CamundaConstants;
import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.camunda.bpm.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TaskInstance}.
 */
@Service
@Transactional
public class TaskInstanceService {

    private final Logger log = LoggerFactory.getLogger(TaskInstanceService.class);

    private final TaskInstanceRepository taskInstanceRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskService taskService;

    private final EntityManager entityManager;

    public TaskInstanceService(
        TaskInstanceRepository taskInstanceRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskService taskService,
        EntityManager entityManager
    ) {
        this.taskInstanceRepository = taskInstanceRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskService = taskService;
        this.entityManager = entityManager;
    }

    /**
     * Save a taskInstance.
     *
     * @param taskInstanceDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskInstanceDTO save(TaskInstanceDTO taskInstanceDTO) {
        log.debug("Request to save TaskInstance : {}", taskInstanceDTO);
        TaskInstance taskInstance = taskInstanceMapper.toEntity(taskInstanceDTO);
        TaskInstance taskInstanceSaved = taskInstanceRepository.save(taskInstance);
        return taskInstanceMapper.toDto(taskInstanceSaved);
    }

    /**
     * Get all the taskInstances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskInstanceDTO> findAll() {
        log.debug("Request to get all TaskInstances");
        return taskInstanceRepository.findAll().stream().map(taskInstanceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskInstanceDTO> findOne(Long id) {
        log.debug("Request to get TaskInstance : {}", id);
        return taskInstanceRepository.findById(id).map(taskInstanceMapper::toDto);
    }

    public Optional<TaskInstanceDTO> claim(Long id) {
        log.debug("Request to claim TaskInstance : {}", id);
        Optional<TaskInstance> optionalTaskInstance = taskInstanceRepository.findById(id);
        if (optionalTaskInstance.isPresent()) {
            TaskInstance taskInstance = optionalTaskInstance.get();
            taskInstance.setStatus(StatusTaskInstance.ASSIGNED);
            taskInstance.setAssignee(SecurityUtils.getCurrentUserLogin().get());
            taskService.claim(taskInstance.getTaskId(), SecurityUtils.getCurrentUserLogin().get());
            taskInstanceRepository.save(taskInstance);
        }
        return optionalTaskInstance.map(taskInstanceMapper::toDto);
    }

    public void complete(TaskInstanceDTO taskInstanceDTO) {
        ProcessInstanceDTO processInstanceDTO = taskInstanceDTO.getProcessInstance();
        //TODO... updating with data from request...
        //processInstanceService.save(processInstance);
        complete(taskInstanceDTO, processInstanceDTO);
    }

    public void complete(TaskInstanceDTO taskInstance, Object processInstance) {
        log.debug("Concluding taskIntanceId: {}, camundaTaskId: {}", taskInstance.getId(), taskInstance.getTaskId());
        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstance);
        params.put(CamundaConstants.PROCESS_INSTANCE_INITIALS, processInstance);
        taskService.claim(taskInstance.getTaskId(), SecurityUtils.getCurrentUserLogin().get());
        taskService.complete(taskInstance.getTaskId(), params);
    }

    public List<TaskInstanceDTO> findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        log.debug("Request to get TaskInstances of the ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        return taskInstanceRepository
            .findByProcessDefinitionIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .stream()
            .map(taskInstanceMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<TaskInstanceDTO> findByProcessInstance(Long id) {
        log.debug("Request to get TaskInstances of the ProcessInstance : {}", id);
        return taskInstanceRepository.findByProcessInstanceId(id).stream().map(taskInstanceMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskInstanceDTO> getMyCandidateTaskInstances() {
        StringBuilder hql = new StringBuilder();
        hql.append(" from ");
        hql.append(TaskInstance.class.getSimpleName());
        hql.append(" where ");
        hql.append(" status in :statusList ");
        hql.append(" and ( ");
        hql.append(" candidateGroups is null ");
        for (String authority : SecurityUtils.getAuthorities()) {
            hql.append(" or candidateGroups like '%," + authority + ",%' ");
        }
        hql.append(" ) ");
        List<TaskInstance> taskInstances = (List<TaskInstance>) entityManager
            .createQuery(hql.toString())
            .setParameter(
                "statusList",
                Arrays.asList(
                    StatusTaskInstance.NEW,
                    StatusTaskInstance.ASSIGNED,
                    StatusTaskInstance.DELEGATED,
                    StatusTaskInstance.UNASSIGNED
                )
            )
            .getResultList();
        return taskInstances.stream().map(taskInstanceMapper::toDto).collect(Collectors.toList());
    }


    public List<TaskInstanceDTO> getMyAssignedTaskInstances() {
        log.debug("Request to get myAssignedTaskInstances: {}", SecurityUtils.getCurrentUserLogin().get());
        return taskInstanceRepository
            .findByAssigneeAndStatusIn(
                SecurityUtils.getCurrentUserLogin().get(),
                Arrays.asList(
                    StatusTaskInstance.NEW,
                    StatusTaskInstance.ASSIGNED,
                    StatusTaskInstance.DELEGATED,
                    StatusTaskInstance.UNASSIGNED
                )
            )
            .stream()
            .map(taskInstanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
