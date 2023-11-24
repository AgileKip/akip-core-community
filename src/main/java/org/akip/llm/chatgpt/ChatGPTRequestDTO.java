package org.akip.llm.chatgpt;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTRequestDTO {

    private String model;

    private List<ChatGPTMessageDTO> messages = new ArrayList<>();

    private Float temperature;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }



    public List<ChatGPTMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatGPTMessageDTO> messages) {
        this.messages = messages;
    }


    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public ChatGPTRequestDTO model(String model) {
        this.model = model;
        return this;
    }

    public ChatGPTRequestDTO addMessage(ChatGPTMessageDTO message) {
        this.messages.add(message);
        return this;
    }

    public ChatGPTRequestDTO temperature(Float temperature) {
        this.temperature = temperature;
        return this;
    }
}
