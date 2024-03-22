package org.akip.dao;

import com.owse.searchFramework.AbstractDAO;
import com.owse.searchFramework.FilterDef;
import com.owse.searchFramework.ResultColumn;
import com.owse.searchFramework.StringFilterDef;
import com.owse.searchFramework.enumeration.FilterType;
import org.akip.domain.TaskDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service("taskDefinition")
class TaskDefinitionDAO extends AbstractDAO<TaskDefinitionSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(TaskDefinitionDAO.class);

    TaskDefinitionDAO(EntityManager entityManager) {
        super(entityManager, TaskDefinition.class, TaskDefinitionSearchDTO.class);
    }

    @Override
    public List<String> getConstructorFields() {
        List<String> fields = new ArrayList<>();
        fields.add("entity.id");
        fields.add("entity.taskId");
        fields.add("entity.name");
        fields.add("entity.bpmnProcessDefinitionId");
        return fields;
    }

    @Override
    public List<FilterDef> getFiltersDef() {

        List<FilterDef> filters = new ArrayList<>();

        StringFilterDef taskIdFilterDef = new StringFilterDef();
        taskIdFilterDef.setId("taskId");
        taskIdFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(taskIdFilterDef);

        StringFilterDef nameFilterDef = new StringFilterDef();
        nameFilterDef.setId("name");
        nameFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(nameFilterDef);

        StringFilterDef bpmnProcessDefinitionIdFilterDef = new StringFilterDef();
        bpmnProcessDefinitionIdFilterDef.setId("bpmnProcessDefinitionId");
        bpmnProcessDefinitionIdFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(bpmnProcessDefinitionIdFilterDef);

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
        resultColumnTaskId.setTitle("Task ID");
        resultColumnTaskId.setDtoField("taskId");
        resultColumnTaskId.setVisible(true);
        resultColumnTaskId.setType("String");
        resultColumns.add(resultColumnTaskId);

        ResultColumn resultColumnName = new ResultColumn();
        resultColumnName.setId("name");
        resultColumnName.setTitle("Name");
        resultColumnName.setDtoField("name");
        resultColumnName.setVisible(true);
        resultColumnName.setType("String");
        resultColumns.add(resultColumnName);

        ResultColumn resultColumnBpmnProcessDefinitionId = new ResultColumn();
        resultColumnBpmnProcessDefinitionId.setId("bpmnProcessDefinitionId");
        resultColumnBpmnProcessDefinitionId.setTitle("Bpmn Process Definition Id");
        resultColumnBpmnProcessDefinitionId.setDtoField("bpmnProcessDefinitionId");
        resultColumnBpmnProcessDefinitionId.setVisible(true);
        resultColumnBpmnProcessDefinitionId.setType("String");
        resultColumns.add(resultColumnBpmnProcessDefinitionId);

        return resultColumns;
    }

    @Override
    public String getDefaultSortByField() {
        return "id";
    }

    @Override
    public void configureCustomHQLFields() {

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
