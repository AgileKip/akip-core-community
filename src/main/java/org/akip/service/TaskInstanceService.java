package org.akip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.camunda.CamundaConstants;
import org.akip.delegate.RedoableDelegate;
import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.exception.ClaimNotAllowedException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.TaskInstanceRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.IProcessEntity;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.akip.service.mapper.TaskInstanceMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TaskInstance}.
 */
@Service
@Transactional
public class TaskInstanceService {

    private final Logger log = LoggerFactory.getLogger(TaskInstanceService.class);

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessInstanceMapper processInstanceMapper;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskService taskService;

    private final NoteService noteService;

    private final EntityManager entityManager;

    private final BeanFactory beanFactory;

    private static final String ANONYMOUS_USER = "anonymousUser";

    public TaskInstanceService(
            ProcessInstanceRepository processInstanceRepository, ProcessInstanceMapper processInstanceMapper, TaskInstanceRepository taskInstanceRepository,
            TaskInstanceMapper taskInstanceMapper,
            TaskService taskService,
            NoteService noteService, EntityManager entityManager,
            BeanFactory beanFactory) {
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
        this.taskInstanceRepository = taskInstanceRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskService = taskService;
        this.noteService = noteService;
        this.entityManager = entityManager;
        this.beanFactory = beanFactory;
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
        try {
            if (optionalTaskInstance.isPresent()) {
                TaskInstance taskInstance = optionalTaskInstance.get();
                checkCurrentUserPermission(taskInstanceMapper.stringToList(taskInstance.getCandidateGroups()));

                taskInstance.setStatus(StatusTaskInstance.ASSIGNED);
                taskInstance.setAssignee(SecurityUtils.getCurrentUserLogin().get());

                //Reset starTine on the first claiming or when changing assignee
                if (taskInstance.getStartTime() == null || (taskInstance.getAssignee() != null && !taskInstance.getAssignee().equals(SecurityUtils.getCurrentUserLogin().get()))) {
                    taskInstance.setStartTime(Instant.now());
                }
                String currentUser = SecurityUtils.getCurrentUserLogin().get();
                taskService.setAssignee(taskInstance.getTaskId(), currentUser);
                taskService.claim(taskInstance.getTaskId(), currentUser);
                taskInstanceRepository.save(taskInstance);
            }
        } catch (ClaimNotAllowedException ex) {
            throw new BadRequestErrorException("Task reserved for users " + ex.getCandidateGroups());
        }
        return optionalTaskInstance.map(taskInstanceMapper::toDTOLoadTaskContext);
    }

    /***
     * Check whether the current user can claim this task according to the candidate group list
     * @param candidateGroups candidateGroups
     */
    private void checkCurrentUserPermission(List<String> candidateGroups) {
        if (candidateGroups.isEmpty()) {
            return;
        }

        if (candidateGroups.contains(ANONYMOUS_USER)) {
            return;
        }

        List<String> authoritiesCurrentUser = SecurityUtils.getAuthorities();
        for (String authority : authoritiesCurrentUser) {
            if (candidateGroups.contains(authority)) {
                return;
            }
        }

        throw new ClaimNotAllowedException(String.join(", ", candidateGroups));
    }

    public void complete(TaskInstanceDTO taskInstanceDTO) {
        complete(taskInstanceDTO, taskInstanceDTO.getProcessInstance());
    }

    public void complete(TaskInstanceDTO taskInstance, ProcessInstanceDTO processInstance) {
        log.debug("Concluding taskIntanceId: {}, camundaTaskId: {}", taskInstance.getId(), taskInstance.getTaskId());
        updateProcessInstanceData(processInstance);
        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstance);
        taskService.claim(taskInstance.getTaskId(), SecurityUtils.getCurrentUserLogin().get());
        taskService.complete(taskInstance.getTaskId(), params);
        noteService.closeNotesAssociatedToEntity(TaskInstance.class.getSimpleName(), taskInstance.getId());
    }

    public void complete(TaskInstanceDTO taskInstance, IProcessEntity processEntity) {
        log.debug("Concluding taskIntanceId: {}, camundaTaskId: {}", taskInstance.getId(), taskInstance.getTaskId());
        updateProcessInstanceData(processEntity.getProcessInstance());
        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);
        taskService.claim(taskInstance.getTaskId(), SecurityUtils.getCurrentUserLogin().orElseThrow());
        taskService.complete(taskInstance.getTaskId(), params);
        noteService.closeNotesAssociatedToEntity(TaskInstance.class.getSimpleName(), taskInstance.getId());
    }

    public void redo(Long id) {
        log.debug("Request to redo TaskInstance : {}", id);
        TaskInstanceDTO taskInstance = taskInstanceRepository.findById(id).map(taskInstanceMapper::toDto).get();

        if (taskInstance.getType() == TypeTaskInstance.USER_TASK) {
            throw new BadRequestErrorException("redoNotAllowedForUserTask", "taskInstance", "redoNotAllowedForUserTask");
        }

        if (StringUtils.isBlank(taskInstance.getConnectorName())) {
            throw new BadRequestErrorException("noDelegateNameInTheTaskInstance", "taskInstance", "noDelegateNameInTheTaskInstance");
        }

        JavaDelegate delegate = (JavaDelegate) beanFactory.getBean(taskInstance.getConnectorName());
        if (delegate instanceof RedoableDelegate) {
            ((RedoableDelegate) delegate).redo(taskInstance);
        }
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

    private void updateProcessInstanceData(ProcessInstanceDTO processInstanceDTO) {
        try {
            processInstanceRepository.updateDataById(
                    processInstanceMapper.mapToString(processInstanceDTO.getData()),
                    processInstanceDTO.getId()
            );
        } catch (JsonProcessingException e) {
            throw new BadRequestErrorException(e.toString());
        }
    }
}
