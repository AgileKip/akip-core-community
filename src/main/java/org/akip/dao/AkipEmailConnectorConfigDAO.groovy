package org.akip.dao

import com.owse.searchFramework.*
import com.owse.searchFramework.enumeration.FilterType
import org.akip.domain.AkipEmailConnectorConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import javax.persistence.EntityManager

@Service("akipEmailConnectorConfig")
class AkipEmailConnectorConfigDAO extends AbstractDAO<AkipEmailConnectorConfigSearchDTO> {

	private final Logger log = LoggerFactory.getLogger(AkipEmailConnectorConfigDAO.class)

    AkipEmailConnectorConfigDAO(EntityManager entityManager) {
        super(entityManager, AkipEmailConnectorConfig.class, AkipEmailConnectorConfigSearchDTO.class)
    }

    @Override
    public List<String> getConstructorFields() {
        List<String> fields = []
        fields << "entity.id"
        fields << "entity.name"
        return fields;
    }

    @Override
    void configureCustomHQLFields() {

    }

    @Override
    List<FilterDef> getFiltersDef() {
        List<Filter> filters = []
        filters << new StringFilterDef(id: 'name', filterType: FilterType.DEFAULT)
        return filters
    }

    @Override
    List<ResultColumn> getResultColumns() {
        List<ResultColumn> resultColumns = []
        resultColumns << new ResultColumn(id:'id', title: 'Id', dtoField: 'id', visible: true, type: 'String')
        resultColumns << new ResultColumn(id:'name', title: 'Name', dtoField: 'name', visible: true, type: 'String')
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
