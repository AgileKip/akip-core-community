package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.io.Serializable;

/**
 * A AkipPromptConfiguration.
 */
@Entity
@Table(name = "akip_prompt_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipPromptConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @DecimalMin(value = "0")
    @DecimalMax(value = "2")
    @Column(name = "temperature")
    private Float temperature;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    @Column(name = "top_p")
    private Float topP;

    @Column(name = "max_tokens")
    private Integer maxTokens;

    @DecimalMin(value = "-2")
    @DecimalMax(value = "2")
    @Column(name = "presence_penalty")
    private Float presencePenalty;

    @DecimalMin(value = "-2")
    @DecimalMax(value = "2")
    @Column(name = "frequency_penalty")
    private Float frequencyPenalty;

    @Lob
    @Column(name = "params")
    private String params;

    @Lob
    @Column(name = "messages")
    private String messages;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AkipPromptConfiguration id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AkipPromptConfiguration name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public AkipPromptConfiguration model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getTemperature() {
        return this.temperature;
    }

    public AkipPromptConfiguration temperature(Float temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTopP() {
        return this.topP;
    }

    public AkipPromptConfiguration topP(Float topP) {
        this.topP = topP;
        return this;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public Integer getMaxTokens() {
        return this.maxTokens;
    }

    public AkipPromptConfiguration maxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
        return this;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Float getPresencePenalty() {
        return this.presencePenalty;
    }

    public AkipPromptConfiguration presencePenalty(Float presencePenalty) {
        this.presencePenalty = presencePenalty;
        return this;
    }

    public void setPresencePenalty(Float presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public Float getFrequencyPenalty() {
        return this.frequencyPenalty;
    }

    public AkipPromptConfiguration frequencyPenalty(Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
        return this;
    }

    public void setFrequencyPenalty(Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public String getParams() {
        return this.params;
    }

    public AkipPromptConfiguration params(String params) {
        this.params = params;
        return this;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMessages() {
        return this.messages;
    }

    public AkipPromptConfiguration messages(String messages) {
        this.messages = messages;
        return this;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipPromptConfiguration)) {
            return false;
        }
        return id != null && id.equals(((AkipPromptConfiguration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipPromptConfiguration{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
}
