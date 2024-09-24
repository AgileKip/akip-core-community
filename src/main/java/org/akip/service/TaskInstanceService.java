package org.akip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.akip.camunda.CamundaConstants;
import org.akip.delegate.RedoableDelegate;
import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.ProcessVisibilityType;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.groovy.BindingBuilder;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.TaskInstanceRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.*;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.akip.service.mapper.TaskInstanceMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
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

    private final ProcessMemberService processMemberService;

    private final TenantMemberService tenantMemberService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskService taskService;

    private final NoteService noteService;

    private final EntityManager entityManager;

    private final BeanFactory beanFactory;

    private final BindingBuilder bindingBuilder;

    private final RuntimeService runtimeService;

    private static final String ANONYMOUS_USER = "anonymousUser";

    public TaskInstanceService(
            ProcessInstanceRepository processInstanceRepository, ProcessInstanceMapper processInstanceMapper, ProcessMemberService processMemberService, TenantMemberService tenantMemberService, TaskInstanceRepository taskInstanceRepository,
            TaskInstanceMapper taskInstanceMapper,
            TaskService taskService,
            NoteService noteService, EntityManager entityManager,
            BeanFactory beanFactory, BindingBuilder bindingBuilder, RuntimeService runtimeService) {
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
        this.processMemberService = processMemberService;
        this.tenantMemberService = tenantMemberService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskService = taskService;
        this.noteService = noteService;
        this.entityManager = entityManager;
        this.beanFactory = beanFactory;
        this.bindingBuilder = bindingBuilder;
        this.runtimeService = runtimeService;
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

    public String executeDocumentationExpression(TaskInstance taskInstance) {

        Object processEntity = runtimeService.getVariable(taskInstance.getProcessInstance().getCamundaProcessInstanceId(), CamundaConstants.PROCESS_ENTITY);

        if (processEntity != null){
            return executeDocumentationExpressionFromProcessEntity(taskInstance, processEntity);
        }

        Object processInstance = runtimeService.getVariable(taskInstance.getProcessInstance().getCamundaProcessInstanceId(), CamundaConstants.PROCESS_INSTANCE);

        return executeDocumentationExpressionFromProcessInstance(taskInstance, processInstance);


    }

    public String executeDocumentationExpressionFromProcessEntity(TaskInstance taskInstance, Object processEntity) {

        Binding binding = bindingBuilder.buildBindingFromProcessEntity(processEntity);
        GroovyShell shell = new GroovyShell(binding);

        String expression = taskInstance.getTaskDefinition().getDocumentation();

        if (!expression.contains("\"\"\"")) {
            expression = "\"\"\"" + expression + "\"\"\"";
        }

        return shell.evaluate(expression).toString();
    }

    public String executeDocumentationExpressionFromProcessInstance(TaskInstance taskInstance, Object processInstance) {

        if (taskInstance.getTaskDefinition().getDocumentation() == null){
            return "";
        }

        Binding binding = bindingBuilder.buildBindingFromProcessInstance(processInstance);
        GroovyShell shell = new GroovyShell(binding);

        String expression = taskInstance.getTaskDefinition().getDocumentation();

        if (!expression.contains("\"\"\"")) {
            expression = "\"\"\"" + expression + "\"\"\"";
        }

        return shell.evaluate(expression).toString();
    }

    public Optional<TaskInstanceDTO> claim(Long id) {
        log.debug("Request to claim TaskInstance : {}", id);
        Optional<TaskInstance> optionalTaskInstance = taskInstanceRepository.findById(id);
        if (optionalTaskInstance.isPresent()) {
            TaskInstance taskInstance = optionalTaskInstance.get();

            taskInstance.setDescription(executeDocumentationExpression(taskInstance));

            checkCurrentUserPermission(taskInstanceMapper.stringToList(taskInstance.getComputedCandidateGroups()), taskInstance.getProcessDefinition().getProcessVisibilityType());

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
        return optionalTaskInstance.map(taskInstanceMapper::toDTOLoadTaskContext);
    }

    /***
     * Check whether the current user can claim this task according to the candidate group list
     * @param computedCandidateGroups candidateGroups
     */
    private void checkCurrentUserPermission(List<String> computedCandidateGroups, ProcessVisibilityType processVisibilityType) {
        if (computedCandidateGroups.isEmpty()) {
            return;
        }

        if (computedCandidateGroups.contains(ANONYMOUS_USER)) {
            return;
        }

        for (String authority : getAuthorities(processVisibilityType)) {
            if (computedCandidateGroups.contains(authority)) {
                return;
            }
        }

        throw new BadRequestErrorException("Task reserved for users " + String.join(", ", computedCandidateGroups));
    }

    private List<String> getAuthorities(ProcessVisibilityType processVisibilityType){
        if (ProcessVisibilityType.PUBLIC.equals(processVisibilityType)){
            List<String> authorities = new ArrayList<>();
            //add wildcard for public processes
            authorities.add("*");
            authorities.addAll(SecurityUtils.getAuthorities());
            return authorities;
        }
        if (ProcessVisibilityType.PRIVATE.equals(processVisibilityType)){
            return processMemberService.getProcessRolesByUsername(SecurityUtils.getCurrentUserLogin().get());
        }

        return tenantMemberService.getTenantRolesByUsername(SecurityUtils.getCurrentUserLogin().get());
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
