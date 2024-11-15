package org.akip.delegate.executor;

import org.akip.service.dto.AkipEmailConnectorConfigDTO;

public class AkipEmailConnectorConfigTestRequestDTO {

    private String processEntityName;
    private Long processEntityId;
    private AkipEmailConnectorConfigDTO akipEmailConnectorConfig;

    public String getProcessEntityName() {
        return processEntityName;
    }

    public void setProcessEntityName(String processEntityName) {
        this.processEntityName = processEntityName;
    }

    public Long getProcessEntityId() {
        return processEntityId;
    }

    public void setProcessEntityId(Long processEntityId) {
        this.processEntityId = processEntityId;
    }

    public AkipEmailConnectorConfigDTO getAkipEmailConnectorConfig() {
        return akipEmailConnectorConfig;
    }

    public void setAkipEmailConnectorConfig(AkipEmailConnectorConfigDTO akipEmailConnectorConfig) {
        this.akipEmailConnectorConfig = akipEmailConnectorConfig;
    }
}
