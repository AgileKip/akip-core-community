package org.akip.dao;

import com.owse.searchFramework.*;
import com.owse.searchFramework.enumeration.FilterType;
import org.akip.dao.filter.AssigneeAndCandidateGroupFilter;
import org.akip.dao.filter.TenantFilter;
import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.repository.TenantMemberRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.ProcessDefinitionService;
import org.akip.service.ProcessMemberService;
import org.akip.service.TenantMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("myTaskInstance")
class MyTaskInstanceDAO extends AbstractDAO<TaskInstanceSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(MyTaskInstanceDAO.class);

    private final ProcessDefinitionService processDefinitionService;

    private final TenantMemberRepository tenantMemberRepository;

    private final TenantMemberService tenantMemberService;

    private final ProcessMemberService processMemberService;

    MyTaskInstanceDAO(EntityManager entityManager, ProcessDefinitionService processDefinitionService, TenantMemberRepository tenantMemberRepository, TenantMemberService tenantMemberService, ProcessMemberService processMemberService) {
        super(entityManager, TaskInstance.class, TaskInstanceSearchDTO.class);
        this.processDefinitionService = processDefinitionService;
        this.tenantMemberRepository = tenantMemberRepository;
        this.tenantMemberService = tenantMemberService;
        this.processMemberService = processMemberService;
    }

    @Override
    public PageableSearchResult<TaskInstanceSearchDTO> search(PageableSearchRequest searchRequest) {
        TenantFilter tenantsCurrentUserFilter = new TenantFilter();
        tenantsCurrentUserFilter.setId("tenantObject");
        tenantsCurrentUserFilter.setValues(tenantMemberRepository.findTenantByTenantMemberUsername(SecurityUtils.getCurrentUserLogin().get()));

        List<String> authorities = new ArrayList<>();

        authorities.add("*");
        authorities.addAll(SecurityUtils.getAuthorities());
        authorities.addAll(processMemberService.getProcessRolesByUsername(SecurityUtils.getCurrentUserLogin().get()));
        authorities.addAll(tenantMemberService.getTenantRolesByUsername(SecurityUtils.getCurrentUserLogin().get()));

        AssigneeAndCandidateGroupFilter assigneeAndCandidateGroupFilter = new AssigneeAndCandidateGroupFilter();
        assigneeAndCandidateGroupFilter.setId("assigneeAndCandidateGroupFilter");
        assigneeAndCandidateGroupFilter.setAssignee(SecurityUtils.getCurrentUserLogin().get());
        assigneeAndCandidateGroupFilter.setValues(authorities);

        EnumFilter userTaskFilter = new EnumFilter();
        userTaskFilter.setId("type");
        userTaskFilter.setEnumType(TypeTaskInstance.class);
        userTaskFilter.setValue(TypeTaskInstance.USER_TASK.toString());

        searchRequest.getFilters().add(tenantsCurrentUserFilter);
        searchRequest.getFilters().add(assigneeAndCandidateGroupFilter);
        searchRequest.getFilters().add(userTaskFilter);

        PageableSearchResult<TaskInstanceSearchDTO> result = super.search(searchRequest);

        result.getFilters().remove(tenantsCurrentUserFilter);
        result.getFilters().remove(assigneeAndCandidateGroupFilter);
        result.getFilters().remove(userTaskFilter);

        return result;
    }

    @Override
    public void configureCustomHQLFields() {
        getHqlFields().put("candidateUsers", new HQLField("entity.candidateUsers"));
        getHqlFields().put("candidateGroups", new HQLField("entity.candidateGroups"));
        getHqlFields().put("processDefinition", new HQLField("entity.processDefinition.id", "entity.processDefinition.name"));
        getHqlFields().put("processInstance", new HQLField("entity.processInstance.businessKey"));
        getHqlFields().put("tenantObject", new HQLField("tenant.id","tenant.name"));
        getHqlFields().put("tenant", new HQLField("tenant.name"));
        getHqlFields().put("camundaDeploymentId", new HQLField("processInstance.camundaDeploymentId"));

        getJoinDefs().add(new JoinDef()
                .join("left outer join entity.processInstance.tenant as tenant")
                .activeByDefault(true));

        getJoinDefs().add(new JoinDef()
                .join("left outer join entity.processDefinition.kipApp as kipApp")
                .activeByDefault(true));
    }

    @Override
    public List<String> getConstructorFields() {
        List<String> fields = new ArrayList<>();
        fields.add("entity.id");
        fields.add("entity.taskId");
        fields.add("entity.name");
        fields.add("kipApp.baseUrl");
        fields.add("entity.status");
        fields.add("entity.createDate");
        fields.add("entity.startTime");
        fields.add("entity.endTime");
        fields.add("entity.owner");
        fields.add("entity.assignee");
        fields.add("entity.executionId");
        fields.add("entity.taskDefinitionKey");
        fields.add("entity.suspended");
        fields.add("entity.priority");
        fields.add("entity.candidateGroups");
        fields.add("entity.processDefinition.name");
        fields.add("entity.processDefinition.bpmnProcessDefinitionId");
        fields.add("entity.processInstance.businessKey");
        fields.add("tenant.name");
        fields.add("entity.processInstance.camundaDeploymentId");
        return fields;
    }

    @Override
    public List<FilterDef> getFiltersDef() {
        List<FilterDef> filters = new ArrayList<>();

        EntityFilterDef processDefinitionFilter = new EntityFilterDef();
        processDefinitionFilter.setId("processDefinition");
        processDefinitionFilter.setOptions(processDefinitionService.findAll());
        processDefinitionFilter.setFieldInSelect("name");
        processDefinitionFilter.setFilterType(FilterType.DEFAULT);
        filters.add(processDefinitionFilter);

        EnumListFilterDef statusFilterDef = new EnumListFilterDef();
        statusFilterDef.setId("status");
        statusFilterDef.setEnumType(StatusTaskInstance.class);
        statusFilterDef.setOptions(Arrays.asList(StatusTaskInstance.values()));
        statusFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(statusFilterDef);

        StringFilterDef nameFilterDef = new StringFilterDef();
        nameFilterDef.setId("name");
        nameFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(nameFilterDef);

        DatetimeFilterDef createDateFilterDef = new DatetimeFilterDef();
        createDateFilterDef.setId("createDate");
        createDateFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(createDateFilterDef);

        DatetimeFilterDef startTimeFilterDef = new DatetimeFilterDef();
        startTimeFilterDef.setId("startTime");
        startTimeFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(startTimeFilterDef);

        DatetimeFilterDef endTimeFilterDef = new DatetimeFilterDef();
        endTimeFilterDef.setId("endTime");
        endTimeFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(endTimeFilterDef);

        //filters.add(new EntityFilterDef(id: "tenant", options: securityUtils.getTenantsCurrentUser(), fieldInSelect: "name", removable: false)

        return filters;
    }

    @Override
    public List<ResultColumn> getResultColumns() {
        List<ResultColumn> resultColumns = new ArrayList<>();

        ResultColumn resultColumnId = new ResultColumn();
        resultColumnId.setId("id");
        resultColumnId.setTitle("Id");
        resultColumnId.setDtoField("id");
        resultColumnId.setVisible(true);
        resultColumnId.setType("Long");
        resultColumns.add(resultColumnId);

        ResultColumn resultColumnTaskId = new ResultColumn();
        resultColumnTaskId.setId("taskId");
        resultColumnTaskId.setTitle("Task Id");
        resultColumnTaskId.setDtoField("taskId");
        resultColumnTaskId.setVisible(true);
        resultColumnTaskId.setType("String");
        resultColumns.add(resultColumnTaskId);

        ResultColumn resultColumnName = new ResultColumn();
        resultColumnName.setId("name");
        resultColumnName.setTitle("Task Name");
        resultColumnName.setDtoField("name");
        resultColumnName.setVisible(true);
        resultColumnName.setType("String");
        resultColumns.add(resultColumnName);

        ResultColumn resultColumnStatus = new ResultColumn();
        resultColumnStatus.setId("status");
        resultColumnStatus.setTitle("Status");
        resultColumnStatus.setDtoField("status");
        resultColumnStatus.setVisible(true);
        resultColumnStatus.setType("Custom");
        resultColumns.add(resultColumnStatus);

        ResultColumn resultColumnCreateDate = new ResultColumn();
        resultColumnCreateDate.setId("createDate");
        resultColumnCreateDate.setTitle("Create Date");
        resultColumnCreateDate.setDtoField("createDate");
        resultColumnCreateDate.setVisible(true);
        resultColumnCreateDate.setType("Datetime");
        resultColumns.add(resultColumnCreateDate);

        ResultColumn resultColumnStartTime = new ResultColumn();
        resultColumnStartTime.setId("startTime");
        resultColumnStartTime.setTitle("Start Time");
        resultColumnStartTime.setDtoField("startTime");
        resultColumnStartTime.setVisible(true);
        resultColumnStartTime.setType("Datetime");
        resultColumns.add(resultColumnStartTime);

        ResultColumn resultColumnEndTime = new ResultColumn();
        resultColumnEndTime.setId("endTime");
        resultColumnEndTime.setTitle("End Time");
        resultColumnEndTime.setDtoField("endTime");
        resultColumnEndTime.setVisible(true);
        resultColumnEndTime.setType("Datetime");
        resultColumns.add(resultColumnEndTime);

        ResultColumn resultColumnOwner = new ResultColumn();
        resultColumnOwner.setId("owner");
        resultColumnOwner.setTitle("Owner");
        resultColumnOwner.setDtoField("owner");
        resultColumnOwner.setVisible(false);
        resultColumnOwner.setType("String");
        resultColumns.add(resultColumnOwner);

        ResultColumn resultColumnAssignee = new ResultColumn();
        resultColumnAssignee.setId("assignee");
        resultColumnAssignee.setTitle("Assignee");
        resultColumnAssignee.setDtoField("assignee");
        resultColumnAssignee.setVisible(true);
        resultColumnAssignee.setType("String");
        resultColumns.add(resultColumnAssignee);

        ResultColumn resultColumnExecutionId = new ResultColumn();
        resultColumnExecutionId.setId("executionId");
        resultColumnExecutionId.setTitle("Execution Id");
        resultColumnExecutionId.setDtoField("executionId");
        resultColumnExecutionId.setVisible(false);
        resultColumnExecutionId.setType("String");
        resultColumns.add(resultColumnExecutionId);

        ResultColumn resultColumnTaskDefinitionKey = new ResultColumn();
        resultColumnTaskDefinitionKey.setId("taskDefinitionKey");
        resultColumnTaskDefinitionKey.setTitle("Task Definition Key");
        resultColumnTaskDefinitionKey.setDtoField("taskDefinitionKey");
        resultColumnTaskDefinitionKey.setVisible(false);
        resultColumnTaskDefinitionKey.setType("String");
        resultColumns.add(resultColumnTaskDefinitionKey);

        ResultColumn resultColumnSuspended = new ResultColumn();
        resultColumnSuspended.setId("suspended");
        resultColumnSuspended.setTitle("Suspended");
        resultColumnSuspended.setDtoField("suspended");
        resultColumnSuspended.setVisible(false);
        resultColumnSuspended.setType("String");
        resultColumns.add(resultColumnSuspended);

        ResultColumn resultColumnPriority = new ResultColumn();
        resultColumnPriority.setId("priority");
        resultColumnPriority.setTitle("Priority");
        resultColumnPriority.setDtoField("priority");
        resultColumnPriority.setVisible(false);
        resultColumnPriority.setType("String");
        resultColumns.add(resultColumnPriority);

        ResultColumn resultColumnCandidateGroups = new ResultColumn();
        resultColumnCandidateGroups.setId("candidateGroups");
        resultColumnCandidateGroups.setTitle("Candidate Groups");
        resultColumnCandidateGroups.setDtoField("candidateGroups");
        resultColumnCandidateGroups.setVisible(false);
        resultColumnCandidateGroups.setType("Custom");
        resultColumns.add(resultColumnCandidateGroups);

        ResultColumn resultColumnProcessDefinitionName = new ResultColumn();
        resultColumnProcessDefinitionName.setId("processDefinition");
        resultColumnProcessDefinitionName.setTitle("Process Definition");
        resultColumnProcessDefinitionName.setDtoField("processDefinitionName");
        resultColumnProcessDefinitionName.setVisible(true);
        resultColumnProcessDefinitionName.setType("String");
        resultColumns.add(resultColumnProcessDefinitionName);

        ResultColumn resultColumnProcessInstanceBusinessKey = new ResultColumn();
        resultColumnProcessInstanceBusinessKey.setId("processInstance");
        resultColumnProcessInstanceBusinessKey.setTitle("Process Instance");
        resultColumnProcessInstanceBusinessKey.setDtoField("processInstanceBusinessKey");
        resultColumnProcessInstanceBusinessKey.setVisible(true);
        resultColumnProcessInstanceBusinessKey.setType("String");
        resultColumns.add(resultColumnProcessInstanceBusinessKey);

        ResultColumn resultColumnProcessInstanceTenant = new ResultColumn();
        resultColumnProcessInstanceTenant.setId("tenant");
        resultColumnProcessInstanceTenant.setTitle("Tenant");
        resultColumnProcessInstanceTenant.setDtoField("processInstanceTenantName");
        resultColumnProcessInstanceTenant.setVisible(false);
        resultColumnProcessInstanceTenant.setType("String");
        resultColumns.add(resultColumnProcessInstanceTenant);

        ResultColumn resultColumnProcessInstanceCamundaDeploymentId = new ResultColumn();
        resultColumnProcessInstanceCamundaDeploymentId.setId("camundaDeploymentId");
        resultColumnProcessInstanceCamundaDeploymentId.setTitle("Deploy Id");
        resultColumnProcessInstanceCamundaDeploymentId.setDtoField("processInstanceCamundaDeploymentId");
        resultColumnProcessInstanceCamundaDeploymentId.setVisible(false);
        resultColumnProcessInstanceCamundaDeploymentId.setType("String");
        resultColumns.add(resultColumnProcessInstanceCamundaDeploymentId);

        return resultColumns;
    }

    @Override
    public String getDefaultSortByField() {
        return "taskId";
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public List<String> getParamsId() {
        return null;
    }

}
