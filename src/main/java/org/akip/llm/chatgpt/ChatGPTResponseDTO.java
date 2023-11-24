package org.akip.llm.chatgpt;

import java.util.List;

public class ChatGPTResponseDTO {
    private String id;
    private String object;
    private String created;
    private String model;
    private List<ChatGPTResponseChoiceDTO> choices;
    private ChatGPTResponseUsageDTO usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChatGPTResponseChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChatGPTResponseChoiceDTO> choices) {
        this.choices = choices;
    }

    public ChatGPTResponseUsageDTO getUsage() {
        return usage;
    }

    public void setUsage(ChatGPTResponseUsageDTO usage) {
        this.usage = usage;
    }
}
