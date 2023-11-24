package org.akip.llm.chatgpt;

public class ChatGPTResponseChoiceDTO {

    private Integer index;

    private ChatGPTMessageDTO message;

    private String finishReason;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ChatGPTMessageDTO getMessage() {
        return message;
    }

    public void setMessage(ChatGPTMessageDTO message) {
        this.message = message;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
