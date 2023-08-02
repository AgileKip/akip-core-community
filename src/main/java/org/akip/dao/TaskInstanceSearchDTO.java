package org.akip.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.util.StringToListUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskInstanceSearchDTO {

    private Long id;
    private String taskId;
    private String name;
    private String kipAppBaseUrl;
    private StatusTaskInstance status;
    private Instant createDate;
    private Instant startTime;
    private Instant endTime;
    private String owner;
    private String assignee;
    private String executionId;
    private String taskDefinitionKey;
    private String suspended;
    private Integer priority;
    private List<String> candidateGroups = new ArrayList<>();
    private String processDefinitionName;
    private String processDefinitionBpmnProcessDefinitionId;
    private String processInstanceBusinessKey;
    private String processInstanceTenantName;
    private String processInstanceCamundaDeploymentId;

    private String domainEntityName;
    private Long domainEntityId;

    private Map<String, String> props;
    private BigDecimal rank;
    private Map<String, Object> rankData = new HashMap<>();

    public TaskInstanceSearchDTO(Long id,
                                 String taskId,
                                 String name,
                                 String kipAppBaseUrl,
                                 StatusTaskInstance status,
                                 Instant createDate,
                                 Instant startTime,
                                 Instant endTime,
                                 String owner,
                                 String assignee,
                                 String executionId,
                                 String taskDefinitionKey,
                                 Boolean suspended,
                                 Integer priority,
                                 String candidateGroups,
                                 String processDefinitionName,
                                 String processDefinitionBpmnProcessDefinitionId,
                                 String processInstanceBusinessKey,
                                 String processInstanceTenantName,
                                 String processInstanceCamundaDeploymentId,
                                 String domainEntityName,
                                 Long domainEntityId,
                                 String propsAsString) {

        this.id = id;
        this.taskId = taskId;
        this.name = name;
        this.kipAppBaseUrl = kipAppBaseUrl;
        this.status = status;
        this.createDate = createDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
        this.assignee = assignee;
        this.executionId = executionId;
        this.taskDefinitionKey = taskDefinitionKey;
        this.suspended = (suspended!=null ? suspended.toString() : null);
        this.priority = priority;
        this.candidateGroups = StringToListUtil.stringToList(candidateGroups);
        this.processDefinitionName = processDefinitionName;
        this.processDefinitionBpmnProcessDefinitionId = processDefinitionBpmnProcessDefinitionId;
        this.processInstanceBusinessKey = processInstanceBusinessKey;
        this.processInstanceTenantName = processInstanceTenantName;
        this.processInstanceCamundaDeploymentId = processInstanceCamundaDeploymentId;
        this.domainEntityName = domainEntityName;
        this.domainEntityId = domainEntityId;
        try {
            this.props = new ObjectMapper().readValue(propsAsString, new TypeReference<Map<String, String>>() {});
        } catch (JsonProcessingException e) {
            this.props = new HashMap<>();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKipAppBaseUrl() {
        return kipAppBaseUrl;
    }

    public void setKipAppBaseUrl(String kipAppBaseUrl) {
        this.kipAppBaseUrl = kipAppBaseUrl;
    }

    public StatusTaskInstance getStatus() {
        return status;
    }

    public void setStatus(StatusTaskInstance status) {
        this.status = status;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getSuspended() {
        return suspended;
    }

    public void setSuspended(String suspended) {
        this.suspended = suspended;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<String> getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(List<String> candidateGroups) {
        this.candidateGroups = candidateGroups;
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

    public String getProcessInstanceBusinessKey() {
        return processInstanceBusinessKey;
    }

    public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
        this.processInstanceBusinessKey = processInstanceBusinessKey;
    }

    public String getProcessInstanceTenantName() {
        return processInstanceTenantName;
    }

    public void setProcessInstanceTenantName(String processInstanceTenantName) {
        this.processInstanceTenantName = processInstanceTenantName;
    }

    public String getProcessInstanceCamundaDeploymentId() {
        return processInstanceCamundaDeploymentId;
    }

    public void setProcessInstanceCamundaDeploymentId(String processInstanceCamundaDeploymentId) {
        this.processInstanceCamundaDeploymentId = processInstanceCamundaDeploymentId;
    }

    public String getDomainEntityName() {
        return domainEntityName;
    }

    public void setDomainEntityName(String domainEntityname) {
        this.domainEntityName = domainEntityname;
    }

    public Long getDomainEntityId() {
        return domainEntityId;
    }

    public void setDomainEntityId(Long domainEntityId) {
        this.domainEntityId = domainEntityId;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public BigDecimal getRank() {
        return rank;
    }

    public void setRank(BigDecimal rank) {
        this.rank = rank;
    }

    public Map<String, Object> getRankData() {
        return rankData;
    }

    public void setRankData(Map<String, Object> rankData) {
        this.rankData = rankData;
    }
}
