package org.akip.dao;

import com.owse.searchFramework.*;
import com.owse.searchFramework.enumeration.FilterType;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.searchDto.ProcessInstanceNotificationSearchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("myProcessInstanceNotification")
class MyProcessInstanceNotificationDao extends AbstractDAO<ProcessInstanceNotificationSearchDTO> {

    private final Logger log = LoggerFactory.getLogger(MyProcessInstanceNotificationDao.class);

    MyProcessInstanceNotificationDao(EntityManager entityManager) {
        super(entityManager, ProcessInstanceNotification.class, ProcessInstanceNotificationSearchDTO.class);
    }

    @Override
    public void configureCustomHQLFields() {
        getHqlFields().put("description", new HQLField("entity.description"));
        getHqlFields().put("createDate", new HQLField("entity.createDate"));
        getHqlFields().put("readDate", new HQLField("entity.readDate"));
        getHqlFields().put("status", new HQLField("entity.status"));
        getHqlFields().put("eventType", new HQLField("entity.eventType"));
        getHqlFields().put("userLogin", new HQLField("entity.subscriberId"));
        getHqlFields().put("processInstance", new HQLField("entity.processInstance.processDefinition.name"));

    }

    @Override
    public List<String> getConstructorFields() {
        List<String> fields = new ArrayList<>();
        fields.add("entity.id");
        fields.add("entity.title");
        fields.add("entity.description");
        fields.add("entity.createDate");
        fields.add("entity.readDate");
        fields.add("entity.status");
        fields.add("entity.eventType");
        fields.add("entity.subscriberId");
        fields.add("entity.processInstance.processDefinition.name");

        return fields;
    }

    @Override
    public void handleRequestBeforeSearch(PageableSearchRequest searchRequest) {
        super.handleRequestBeforeSearch(searchRequest);
        StringFilter currentUserFilter = new StringFilter();
        currentUserFilter.setId("userLogin");
        currentUserFilter.setValue(SecurityUtils.getCurrentUserLogin().orElseThrow());
        currentUserFilter.setOperator("exact");
        searchRequest.getFilters().add(currentUserFilter);
    }

    @Override
    public void handleResultAfterSearch(PageableSearchResult<ProcessInstanceNotificationSearchDTO> searchResult) {
        super.handleResultAfterSearch(searchResult);
        Filter currentUserFilter = searchResult
            .getFilters()
            .stream()
            .filter(filter -> filter.getId().equals("userLogin"))
            .findFirst()
            .get();
        searchResult.getFilters().remove(currentUserFilter);
    }

    @Override
    public List<FilterDef> getFiltersDef() {
        List<FilterDef> filters = new ArrayList<>();

        StringFilterDef descriptionFilterDef = new StringFilterDef();
        descriptionFilterDef.setId("description");
        descriptionFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(descriptionFilterDef);

        DatetimeFilterDef createDateFilterDef = new DatetimeFilterDef();
        createDateFilterDef.setId("createDate");
        createDateFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(createDateFilterDef);

        DatetimeFilterDef readDateFilterDef = new DatetimeFilterDef();
        readDateFilterDef.setId("readDate");
        readDateFilterDef.setFilterType(FilterType.ADVANCED);
        filters.add(readDateFilterDef);

        EnumListFilterDef statusFilterDef = new EnumListFilterDef();
        statusFilterDef.setId("status");
        statusFilterDef.setOptions(Arrays.asList(ProcessInstanceNotificationStatus.values()));
        statusFilterDef.setFilterType(FilterType.DEFAULT);
        statusFilterDef.setEnumType(ProcessInstanceNotificationStatus.class);
        filters.add(statusFilterDef);

        EnumListFilterDef eventTypeFilterDef = new EnumListFilterDef();
        eventTypeFilterDef.setId("eventType");
        eventTypeFilterDef.setOptions(Arrays.asList(ProcessInstanceEventType.values()));
        eventTypeFilterDef.setFilterType(FilterType.DEFAULT);
        eventTypeFilterDef.setEnumType(ProcessInstanceEventType.class);
        filters.add(eventTypeFilterDef);

        StringFilterDef processInstanceNameFilterDef = new StringFilterDef();
        processInstanceNameFilterDef.setId("processInstance");
        processInstanceNameFilterDef.setFilterType(FilterType.DEFAULT);
        filters.add(processInstanceNameFilterDef);

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

        ResultColumn resultColumnDescription = new ResultColumn();
        resultColumnDescription.setId("title");
        resultColumnDescription.setTitle("Title");
        resultColumnDescription.setDtoField("title");
        resultColumnDescription.setVisible(true);
        resultColumnDescription.setType("String");
        resultColumns.add(resultColumnDescription);

        ResultColumn resultColumnCreateDate = new ResultColumn();
        resultColumnCreateDate.setId("CreateDate");
        resultColumnCreateDate.setTitle("Create Date");
        resultColumnCreateDate.setDtoField("createDate");
        resultColumnCreateDate.setVisible(true);
        resultColumnCreateDate.setType("Datetime");
        resultColumns.add(resultColumnCreateDate);

        ResultColumn resultColumnReadDate = new ResultColumn();
        resultColumnReadDate.setId("readDate");
        resultColumnReadDate.setTitle("Read Date");
        resultColumnReadDate.setDtoField("readDate");
        resultColumnReadDate.setVisible(true);
        resultColumnReadDate.setType("Datetime");
        resultColumns.add(resultColumnReadDate);

        ResultColumn resultColumnStatus = new ResultColumn();
        resultColumnStatus.setId("status");
        resultColumnStatus.setTitle("Status");
        resultColumnStatus.setDtoField("status");
        resultColumnStatus.setVisible(true);
        resultColumnStatus.setType("Enum");
        resultColumnStatus.setSubType("ProcessInstanceNotificationStatus");
        resultColumns.add(resultColumnStatus);

        ResultColumn resultColumnEventType = new ResultColumn();
        resultColumnEventType.setId("eventType");
        resultColumnEventType.setTitle("Event Type");
        resultColumnEventType.setDtoField("eventType");
        resultColumnEventType.setVisible(true);
        resultColumnEventType.setType("Enum");
        resultColumnEventType.setSubType("ProcessInstanceEventType");
        resultColumns.add(resultColumnEventType);

        ResultColumn resultColumnProcessInstanceName = new ResultColumn();
        resultColumnProcessInstanceName.setId("processInstance");
        resultColumnProcessInstanceName.setTitle("Process");
        resultColumnProcessInstanceName.setDtoField("processInstanceName");
        resultColumnProcessInstanceName.setVisible(true);
        resultColumnProcessInstanceName.setType("String");
        resultColumns.add(resultColumnProcessInstanceName);

        return resultColumns;
    }

    @Override
    public String getDefaultSortByField() {
        return "id";
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
