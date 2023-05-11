package org.akip.dao;

import org.akip.domain.enumeration.StatusProcessInstance;

import java.time.LocalDateTime;

public class ProcessInstanceSearchDTO {

    private Long id;
    private String businessKey;
    private String username;
    private StatusProcessInstance status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String processDefinitionName;
    private String processDefinitionBpmnProcessDefinitionId;
    private String tenantName;
    private String camundaDeploymentId;
    private String kipAppBaseUrl;

    public ProcessInstanceSearchDTO(Long id,
                                    String businessKey,
                                    String username,
                                    StatusProcessInstance status,
                                    LocalDateTime startDate,
                                    LocalDateTime endDate,
                                    String processDefinitionName,
                                    String processDefinitionBpmnProcessDefinitionId,
                                    String tenantName,
                                    String camundaDeploymentId,
                                    String kipAppBaseUrl) {
        this.id = id;
        this.businessKey = businessKey;
        this.username = username;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.processDefinitionName = processDefinitionName;
        this.processDefinitionBpmnProcessDefinitionId = processDefinitionBpmnProcessDefinitionId;
        this.tenantName = tenantName;
        this.camundaDeploymentId = camundaDeploymentId;
        this.kipAppBaseUrl = kipAppBaseUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public StatusProcessInstance getStatus() {
        return status;
    }

    public void setStatus(StatusProcessInstance status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getProcessDefinitionBpmnProcessDefinitionId() {
        return processDefinitionBpmnProcessDefinitionId;
    }

    public void setProcessDefinitionBpmnProcessDefinitionId(String processDefinitionBpmnProcessDefinitionId) {
        this.processDefinitionBpmnProcessDefinitionId = processDefinitionBpmnProcessDefinitionId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getKipAppBaseUrl() {
        return kipAppBaseUrl;
    }

    public void setKipAppBaseUrl(String kipAppBaseUrl) {
        this.kipAppBaseUrl = kipAppBaseUrl;
    }
}
