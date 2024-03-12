package org.akip.service.dto;

import org.akip.domain.ProcessDefinition;

import java.io.Serializable;
import java.util.Objects;

public class ProcessRoleDTO implements Serializable {

    private Long id;

    private String name;

    private ProcessDefinition processDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessRoleDTO that = (ProcessRoleDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProcessRoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
