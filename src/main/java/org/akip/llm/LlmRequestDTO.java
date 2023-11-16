package org.akip.llm;

import java.util.Map;

public class LlmRequestDTO {

    private String promptConfigurationName;

    private String source;

    private Map<String, Object> context;

    private Map<String, Object> params;

    public String getPromptConfigurationName() {
        return promptConfigurationName;
    }

    public void setPromptConfigurationName(String promptConfigurationName) {
        this.promptConfigurationName = promptConfigurationName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
