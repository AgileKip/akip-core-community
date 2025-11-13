package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "process_timeline_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessTimelineDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "condition_expression")
    private String conditionExpression;

    @ManyToOne
    @JoinColumn(name = "bpmnProcessDefinitionId", referencedColumnName = "bpmn_process_definition_id")
    private ProcessDefinition processDefinition;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "processTimelineDefinition")
    @OrderBy("step asc")
    private List<ProcessTimelineItemDefinition> items = new ArrayList<>();

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

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public List<ProcessTimelineItemDefinition> getItems() {
        return items;
    }

    public void setItems(List<ProcessTimelineItemDefinition> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessTimelineDefinition that = (ProcessTimelineDefinition) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(conditionExpression, that.conditionExpression) &&
            Objects.equals(processDefinition, that.processDefinition) &&
            Objects.equals(items, that.items)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, conditionExpression, processDefinition, items);
    }
}
