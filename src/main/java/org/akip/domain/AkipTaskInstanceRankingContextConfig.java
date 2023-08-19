package org.akip.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "akip_ti_ranking_context_config")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipTaskInstanceRankingContextConfig {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "context_value_expression")
    private String contextValueExpression;

    @ManyToOne
    private ProcessDefinition processDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContextValueExpression() {
        return contextValueExpression;
    }

    public void setContextValueExpression(String contextValueExpression) {
        this.contextValueExpression = contextValueExpression;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }
}
