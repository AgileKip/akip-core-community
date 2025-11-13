package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "process_timeline_item_def")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProcessTimelineItemDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "step")
    private int step;

    @Column(name = "name")
    private String name;

    @Column(name = "expression")
    private String checkStatusExpression;

    @ManyToOne
    @JoinColumn(name = "process_timeline_definition_id")
    private ProcessTimelineDefinition processTimelineDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckStatusExpression() {
        return checkStatusExpression;
    }

    public void setCheckStatusExpression(String checkStatusExpression) {
        this.checkStatusExpression = checkStatusExpression;
    }

    public ProcessTimelineDefinition getProcessTimelineDefinition() {
        return processTimelineDefinition;
    }

    public void setProcessTimelineDefinition(ProcessTimelineDefinition processTimelineDefinition) {
        this.processTimelineDefinition = processTimelineDefinition;
    }
}
