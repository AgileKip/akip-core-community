package org.akip.service.dto;

import java.util.Map;

public class DomainEntityDTO {

    private Map<String, Object> data;

    private DomainEntityDefinitionDTO definition;


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public DomainEntityDefinitionDTO getDefinition() {
        return definition;
    }

    public void setDefinition(DomainEntityDefinitionDTO definition) {
        this.definition = definition;
    }
}
