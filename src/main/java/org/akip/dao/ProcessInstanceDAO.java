package org.akip.dao

import com.owse.searchFramework.AbstractDAO
import com.owse.searchFramework.DatetimeFilterDef
import com.owse.searchFramework.EntityFilterDef
import com.owse.searchFramework.EnumListFilterDef
import com.owse.searchFramework.Filter
import com.owse.searchFramework.FilterDef
import com.owse.searchFramework.HQLField
import com.owse.searchFramework.ResultColumn
import com.owse.searchFramework.StringFilterDef
import org.akip.domain.ProcessInstance
import org.akip.domain.enumeration.StatusTaskInstance
import org.akip.service.ProcessDefinitionService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import javax.persistence.EntityManager

@Service("processInstance")
class ProcessInstanceDAO extends AbstractDAO<ProcessInstanceSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceDAO.class)

    private final ProcessDefinitionService processDefinitionService

    ProcessInstanceDAO(EntityManager entityManager, ProcessDefinitionService processDefinitionService) {
        super(entityManager, ProcessInstance.class, ProcessInstanceSearchDTO.class)
        this.processDefinitionService = processDefinitionService
    }

    @Override
    void configureCustomHQLFields() {
        hqlFields.tenant = new HQLField('entity.tenant.id', 'entity.tenant.name')
        hqlFields.processDefinition = new HQLField('entity.processDefinition.id', 'entity.processDefinition.name')
    }

    @Override
    List<String> getConstructorFields() {
        List<String> fields = []
        fields << 'entity.id'
        fields << 'entity.businessKey'
        fields << 'entity.username'
        fields << 'entity.status'
        fields << 'entity.startDate'
        fields << 'entity.endDate'
        fields << 'entity.processDefinition.name'
        fields << 'entity.tenant.name'
        fields << 'entity.camundaDeploymentId'
        return fields
    }

    @Override
    List<FilterDef> getFiltersDef() {
        List<Filter> filters = []
        filters << new EntityFilterDef(id:'processDefinition', options: processDefinitionService.findAll(), fieldInSelect: 'name', removable: false)
        //filters << new EntityFilterDef(id: 'tenant', options: securityUtils.getTenantsCurrentUser(), fieldInSelect: 'name', removable: false)
        filters << new EnumListFilterDef(id:'status', enumType: StatusTaskInstance.class, options: StatusTaskInstance.values(), removable: false)
        filters << new StringFilterDef(id:'username')
        filters << new StringFilterDef(id:'businessKey')
        filters << new DatetimeFilterDef(id:'createTime')

        return filters
    }

    @Override
    List<ResultColumn> getResultColumns() {
        List<ResultColumn> resultColumns = []
        resultColumns << new ResultColumn(id:'id', title: 'Id', dtoField: 'id', visible: true, type: 'Long')
        resultColumns << new ResultColumn(id:'businessKey', title: 'Business Key', dtoField: 'businessKey', visible: true, type: 'String')
        resultColumns << new ResultColumn(id:'username', title: 'Status', dtoField: 'username', visible: true, type: 'String')
        resultColumns << new ResultColumn(id:'status', title: 'Status', dtoField: 'status', visible: true, type: 'Custom', subType: 'akip-show-process-instance-status')
        resultColumns << new ResultColumn(id:'startDate', title: 'Start Date', dtoField: 'startDate', visible: true, type: 'Datetime')
        resultColumns << new ResultColumn(id:'endDate', title: 'End Date', dtoField: 'endDate', visible: true, type: 'Datetime')
        resultColumns << new ResultColumn(id:'processDefinition', title: 'Process', dtoField: 'processDefinitionName', visible: true, type: 'String')
        resultColumns << new ResultColumn(id:'tenant', title: 'Tenant', dtoField: 'tenantName', visible: true, type: 'String')
        resultColumns << new ResultColumn(id:'camundaDeploymentId', title: 'Deployment Id', dtoField: 'camundaDeploymentId', visible: true, type: 'String')
        return resultColumns
    }

    @Override
    String getDefaultSortByField() {
        return "name"
    }

    @Override
    Logger getLog() {
        return log
    }

    @Override
    List<String> getParamsId() {
        return null;
    }

}
