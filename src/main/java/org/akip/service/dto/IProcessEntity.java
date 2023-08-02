package org.akip.service.dto;


public interface IProcessEntity {
    ProcessInstanceDTO getProcessInstance();

    void setProcessInstance(ProcessInstanceDTO processInstanceDTO);

    String getDomainEntityName();

    Long getDomainEntityId();
}
