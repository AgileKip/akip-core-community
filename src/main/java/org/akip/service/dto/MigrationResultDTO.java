package org.akip.service.dto;

public class MigrationResultDTO {

    private String processInstanceId;
    private String statusMessage;

    public MigrationResultDTO(String processInstanceId, String statusMessage) {
        this.processInstanceId = processInstanceId;
        this.statusMessage = statusMessage;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
