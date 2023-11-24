package org.akip.llm;

public class LlmResponseDTO {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LlmResponseDTO content(String content) {
        this.content = content;
        return this;
    }
}
