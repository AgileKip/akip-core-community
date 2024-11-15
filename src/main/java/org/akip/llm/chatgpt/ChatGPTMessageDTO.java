package org.akip.llm.chatgpt;

public class ChatGPTMessageDTO {

    private String role;

    private String content;

    public ChatGPTMessageDTO() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ChatGPTMessageDTO role(String role) {
        this.role = role;
        return this;
    }

    public ChatGPTMessageDTO content(String content) {
        this.content = content;
        return this;
    }
}
