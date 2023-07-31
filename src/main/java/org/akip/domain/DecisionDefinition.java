package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "decision_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DecisionDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @NaturalId
    @Column(name = "dmn_decision_definition_id")
    private String dmnDecisionDefinitionId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "decisionDefinition")
    private List<DecisionDeployment> decisionDeployments = new ArrayList<>();

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

    public String getDmnDecisionDefinitionId() {
        return dmnDecisionDefinitionId;
    }

    public void setDmnDecisionDefinitionId(String dmnDecisionDefinitionId) {
        this.dmnDecisionDefinitionId = dmnDecisionDefinitionId;
    }

    public List<DecisionDeployment> getDecisionDeployments() {
        return decisionDeployments;
    }

    public void setDecisionDeployments(List<DecisionDeployment> decisionDeployments) {
        this.decisionDeployments = decisionDeployments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecisionDefinition)) {
            return false;
        }
        return id != null && id.equals(((DecisionDefinition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "DecisionDefinition{" +
                "id=" + id +
                ", dmnDecisionDefinitionId='" + dmnDecisionDefinitionId + '\'' +
                ", name='" + name + '\'' +
                ", decisionDeployments=" + decisionDeployments +
                '}';
    }
}