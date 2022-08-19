package org.akip.dao;

import com.owse.searchFramework.*;
import com.owse.searchFramework.enumeration.FilterType;
import org.akip.domain.ProcessInstance;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.service.ProcessDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("processInstance")
class ProcessInstanceDAO extends AbstractDAO<ProcessInstanceSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceDAO.class);

    private final ProcessDefinitionService processDefinitionService;

    ProcessInstanceDAO(EntityManager entityManager, ProcessDefinitionService processDefinitionService) {
        super(entityManager, ProcessInstance.class, ProcessInstanceSearchDTO.class);
        this.processDefinitionService = processDefinitionService;
    }

    @Override
    public void configureCustomHQLFields() {
        getHqlFields().put("tenant", new HQLField("entity.tenant.id", "entity.tenant.name"));
        getHqlFields().put("processDefinition", new HQLField("entity.processDefinition.id", "entity.processDefinition.name"));

        getJoinDefs().add(new JoinDef()
                .join("left outer join entity.tenant as tenant")
                .activeByDefault(true));
    }

    @Override
    public List<String> getConstructorFields() {
        List<String> fields = new ArrayList<>();
        fields.add("entity.id");
        fields.add("entity.businessKey");
        fields.add("entity.username");
        fields.add("entity.status");
        fields.add("entity.startDate");
        fields.add("entity.endDate");
        fields.add("entity.processDefinition.name");
        fields.add("entity.processDefinition.bpmnProcessDefinitionId");
        fields.add("entity.tenant.name");
        fields.add("entity.camundaDeploymentId");
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
        statusFilterDef.setEnumType(StatusProcessInstance.class);
        statusFilterDef.setOptions(Arrays.asList(StatusProcessInstance.values()));
        statusFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(statusFilterDef);

        StringFilterDef businessKeyFilterDef = new StringFilterDef();
        businessKeyFilterDef.setId("businessKey");
        businessKeyFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(businessKeyFilterDef);

        StringFilterDef usernameFilterDef = new StringFilterDef();
        usernameFilterDef.setId("username");
        usernameFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(usernameFilterDef);

        DatetimeFilterDef createTimeFilterDef = new DatetimeFilterDef();
        createTimeFilterDef.setId("startDate");
        createTimeFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(createTimeFilterDef);

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

        ResultColumn resultColumnTitle = new ResultColumn();
        resultColumnTitle.setId("businessKey");
        resultColumnTitle.setTitle("Business Key");
        resultColumnTitle.setDtoField("businessKey");
        resultColumnTitle.setVisible(true);
        resultColumnTitle.setType("String");
        resultColumns.add(resultColumnTitle);

        ResultColumn resultColumnUsername = new ResultColumn();
        resultColumnUsername.setId("username");
        resultColumnUsername.setTitle("Owner");
        resultColumnUsername.setDtoField("username");
        resultColumnUsername.setVisible(true);
        resultColumnUsername.setType("String");
        resultColumns.add(resultColumnUsername);

        ResultColumn resultColumnStatus = new ResultColumn();
        resultColumnStatus.setId("status");
        resultColumnStatus.setTitle("Status");
        resultColumnStatus.setDtoField("status");
        resultColumnStatus.setVisible(true);
        resultColumnStatus.setType("Custom");
        resultColumnStatus.setSubType("akip-show-process-instance-status");
        resultColumns.add(resultColumnStatus);

        ResultColumn resultColumnStartDate = new ResultColumn();
        resultColumnStartDate.setId("startDate");
        resultColumnStartDate.setTitle("Start Date");
        resultColumnStartDate.setDtoField("startDate");
        resultColumnStartDate.setVisible(true);
        resultColumnStartDate.setType("Datetime");
        resultColumns.add(resultColumnStartDate);

        ResultColumn resultColumnEndDate = new ResultColumn();
        resultColumnEndDate.setId("endDate");
        resultColumnEndDate.setTitle("End Date");
        resultColumnEndDate.setDtoField("endDate");
        resultColumnEndDate.setVisible(true);
        resultColumnEndDate.setType("Datetime");
        resultColumns.add(resultColumnEndDate);

        ResultColumn resultColumnProcess = new ResultColumn();
        resultColumnProcess.setId("processDefinition");
        resultColumnProcess.setTitle("Process");
        resultColumnProcess.setDtoField("processDefinitionName");
        resultColumnProcess.setVisible(true);
        resultColumnProcess.setType("String");
        resultColumns.add(resultColumnProcess);

        ResultColumn resultColumnTenant = new ResultColumn();
        resultColumnTenant.setId("tenant");
        resultColumnTenant.setTitle("Tenant");
        resultColumnTenant.setDtoField("tenantName");
        resultColumnTenant.setVisible(true);
        resultColumnTenant.setType("String");
        resultColumns.add(resultColumnTenant);

        ResultColumn resultColumnCamundaDeploymentId = new ResultColumn();
        resultColumnCamundaDeploymentId.setId("camundaDeploymentId");
        resultColumnCamundaDeploymentId.setTitle("Deployment Id");
        resultColumnCamundaDeploymentId.setDtoField("camundaDeploymentId");
        resultColumnCamundaDeploymentId.setVisible(false);
        resultColumnCamundaDeploymentId.setType("String");
        resultColumns.add(resultColumnCamundaDeploymentId);

        return resultColumns;
    }

    @Override
    public String getDefaultSortByField() {
        return "businessKey";
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
