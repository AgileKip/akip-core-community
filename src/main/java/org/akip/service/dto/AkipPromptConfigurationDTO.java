package org.akip.service.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.AkipPromptConfiguration} entity.
 */
public class AkipPromptConfigurationDTO implements Serializable {

    private Long id;

    private String name;

    private String model;

    @DecimalMin(value = "0")
    @DecimalMax(value = "2")
    private Float temperature;

    @DecimalMin(value = "0")
    @DecimalMax(value = "1")
    private Float topP;

    private Integer maxTokens;

    @DecimalMin(value = "-2")
    @DecimalMax(value = "2")
    private Float presencePenalty;

    @DecimalMin(value = "-2")
    @DecimalMax(value = "2")
    private Float frequencyPenalty;

    private List<AkipPromptConfigurationParamDTO> params;


    private List<AkipPromptConfigurationMessageDTO> messages;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTopP() {
        return topP;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Float getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Float presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public Float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public List<AkipPromptConfigurationParamDTO> getParams() {
        return params;
    }

    public void setParams(List<AkipPromptConfigurationParamDTO> params) {
        this.params = params;
    }

    public List<AkipPromptConfigurationMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<AkipPromptConfigurationMessageDTO> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipPromptConfigurationDTO)) {
            return false;
        }

        AkipPromptConfigurationDTO chatPromptConfigurationDTO = (AkipPromptConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, chatPromptConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipPromptConfigurationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", model='" + getModel() + "'" +
            "}";
    }
}
