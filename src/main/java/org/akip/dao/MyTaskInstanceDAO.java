package org.akip.dao;

import com.owse.searchFramework.*;
import com.owse.searchFramework.enumeration.FilterType;
import org.akip.dao.filter.AssigneeAndCandidateGroupFilter;
import org.akip.dao.filter.TenantFilter;
import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.CanadaProvince;
import org.akip.domain.enumeration.Department;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.recsys.AkipTaskInstanceRakingAlgorithmInterface;
import org.akip.repository.TenantMemberRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.ProcessDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service("myTaskInstance")
class MyTaskInstanceDAO extends AbstractDAO<TaskInstanceSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(MyTaskInstanceDAO.class);

    private final ProcessDefinitionService processDefinitionService;

    private final TenantMemberRepository tenantMemberRepository;

    private final BeanFactory beanFactory;

    private Boolean sortedByRank = false;

    private Boolean sortedByRankInteger = false;

    MyTaskInstanceDAO(EntityManager entityManager, ProcessDefinitionService processDefinitionService, TenantMemberRepository tenantMemberRepository, BeanFactory beanFactory) {
        super(entityManager, TaskInstance.class, TaskInstanceSearchDTO.class);
        this.processDefinitionService = processDefinitionService;
        this.tenantMemberRepository = tenantMemberRepository;
        this.beanFactory = beanFactory;
    }

    @Override
    public PageableSearchResult<TaskInstanceSearchDTO> search(PageableSearchRequest searchRequest) {
        TenantFilter tenantsCurrentUserFilter = new TenantFilter();
        tenantsCurrentUserFilter.setId("tenantObject");
        tenantsCurrentUserFilter.setValues(tenantMemberRepository.findTenantByTenantMemberUsername(SecurityUtils.getCurrentUserLogin().get()));

        AssigneeAndCandidateGroupFilter assigneeAndCandidateGroupFilter = new AssigneeAndCandidateGroupFilter();
        assigneeAndCandidateGroupFilter.setId("assigneeAndCandidateGroupFilter");
        assigneeAndCandidateGroupFilter.setAssignee(SecurityUtils.getCurrentUserLogin().get());
        assigneeAndCandidateGroupFilter.setValues(SecurityUtils.getAuthorities());

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
        getHqlFields().put("province", new HQLField("quotationProcess.quotation.province"));
        getHqlFields().put("department", new HQLField("quotationProcess.quotation.department.name"));

        getJoinDefs().add(new JoinDef()
                .join("left outer join entity.processInstance.tenant as tenant")
                .activeByDefault(true));

        getJoinDefs().add(new JoinDef()
                .join("left outer join entity.processDefinition.kipApp as kipApp")
                .activeByDefault(true));

        getJoinDefs().add(new JoinDef()
                .join("left outer join QuotationProcess as quotationProcess with quotationProcess.processInstance.id = entity.processInstance.id")
                .addRelatedFilter("province")
                .addRelatedFilter("department"));
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
        fields.add("entity.processInstance.id");
        fields.add("entity.processInstance.businessKey");
        fields.add("tenant.name");
        fields.add("entity.processInstance.camundaDeploymentId");
        fields.add("entity.processInstance.startDate");
        fields.add("entity.domainEntityName");
        fields.add("entity.domainEntityId");
        fields.add("entity.props");
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

        StringListFilterDef provinceFilterDef = new StringListFilterDef();
        provinceFilterDef.setId("province");
        provinceFilterDef.setFilterType(FilterType.DEFAULT);
        provinceFilterDef.setOptions(Arrays.stream(CanadaProvince.values()).collect(Collectors.toList()));
        filters.add(provinceFilterDef);

        StringListFilterDef departmentFilterDef = new StringListFilterDef();
        departmentFilterDef.setId("department");
        departmentFilterDef.setFilterType(FilterType.DEFAULT);
        departmentFilterDef.setOptions(Arrays.stream(Department.values()).collect(Collectors.toList()));
        filters.add(departmentFilterDef);

        DatetimeFilterDef createDateFilterDef = new DatetimeFilterDef();
        createDateFilterDef.setId("createDate");
        createDateFilterDef.setFilterType(FilterType.DEFAULT);
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

        ResultColumn resultColumnProps = new ResultColumn();
        resultColumnProps.setId("props");
        resultColumnProps.setTitle("Props");
        resultColumnProps.setDtoField("props");
        resultColumnProps.setVisible(false);
        resultColumnProps.setType("Custom");
        resultColumns.add(resultColumnProps);

        ResultColumn resultColumnRank = new ResultColumn();
        resultColumnRank.setId("rank");
        resultColumnRank.setTitle("Rank");
        resultColumnRank.setDtoField("rank");
        resultColumnRank.setVisible(false);
        resultColumnRank.setType("String");
        resultColumns.add(resultColumnRank);

        ResultColumn resultColumnRankInteger = new ResultColumn();
        resultColumnRankInteger.setId("rankInteger");
        resultColumnRankInteger.setTitle("Rank");
        resultColumnRankInteger.setDtoField("rankInteger");
        resultColumnRankInteger.setVisible(true);
        resultColumnRankInteger.setType("String");
        resultColumns.add(resultColumnRankInteger);

        ResultColumn resultColumnRankData = new ResultColumn();
        resultColumnRankData.setId("rankData");
        resultColumnRankData.setTitle("Rank Data");
        resultColumnRankData.setDtoField("rankData");
        resultColumnRankData.setVisible(false);
        resultColumnRankData.setType("Custom");
        resultColumns.add(resultColumnRankData);


        ResultColumn resultColumnDomainEntityName = new ResultColumn();
        resultColumnDomainEntityName.setId("domainEntityName");
        resultColumnDomainEntityName.setTitle("Domain Entity");
        resultColumnDomainEntityName.setDtoField("domainEntityName");
        resultColumnDomainEntityName.setVisible(false);
        resultColumnDomainEntityName.setType("String");
        resultColumns.add(resultColumnDomainEntityName);

        ResultColumn resultColumnDomainEntityId = new ResultColumn();
        resultColumnDomainEntityId.setId("domainEntityId");
        resultColumnDomainEntityId.setTitle("Domain Entity Id");
        resultColumnDomainEntityId.setDtoField("domainEntityId");
        resultColumnDomainEntityId.setVisible(false);
        resultColumnDomainEntityId.setType("String");
        resultColumns.add(resultColumnDomainEntityId);

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

    @Override
    public void handleRequestBeforeSearch(PageableSearchRequest searchRequest) {
        HashMap<String, Object> sortedByMap = (HashMap<String, Object>) searchRequest.getSortedBy();
        sortedByRank = sortedByMap.get("id").equals("rank");
        sortedByRankInteger = sortedByMap.get("id").equals("rankInteger");
        if (sortedByRank || sortedByRankInteger) {
            sortedByMap.put("id", "id");
        }
        super.handleRequestBeforeSearch(searchRequest);
    }

    @Override
    public void handleResultAfterSearch(PageableSearchResult<TaskInstanceSearchDTO> searchResult) {
        super.handleResultAfterSearch(searchResult);

        if (!beanFactory.containsBean("akipRankingAlgorithm")) {
            getLog().info("No bean named 'akipRankingAlgorithm' configured. No ranking will be build.");
            return;
        }

        AkipTaskInstanceRakingAlgorithmInterface rakingAlgorithm = (AkipTaskInstanceRakingAlgorithmInterface) beanFactory.getBean("akipRankingAlgorithm");
        rakingAlgorithm.buildRanking(searchResult.getList());

        if (sortedByRank) {
            HashMap<String, Object> sortedByMap = ((HashMap<String, Object>) searchResult.getSortedBy());
            sortedByMap.put("id", "rank");
            if (Boolean.TRUE.equals(sortedByMap.get("reverse"))) {
                searchResult.getList().sort(Comparator.comparing(TaskInstanceSearchDTO::getRank).reversed());
            } else {
                searchResult.getList().sort(Comparator.comparing(TaskInstanceSearchDTO::getRank));
            }
        }

        if (sortedByRankInteger) {
            HashMap<String, Object> sortedByMap = (HashMap<String, Object>) searchResult.getSortedBy();
            sortedByMap.put("id", "rankInteger");
            if (Boolean.TRUE.equals(sortedByMap.get("reverse"))) {
                searchResult.getList().sort(Comparator.comparingInt(TaskInstanceSearchDTO::getRankInteger).reversed());
            } else {
                searchResult.getList().sort(Comparator.comparingInt(TaskInstanceSearchDTO::getRankInteger));
            }
        }
    }

}



