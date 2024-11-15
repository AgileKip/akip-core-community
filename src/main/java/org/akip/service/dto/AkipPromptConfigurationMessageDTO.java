package org.akip.service.dto;

public class AkipPromptConfigurationMessageDTO {

    private String role;

    private String contentExpression;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContentExpression() {
        return contentExpression;
    }

    public void setContentExpression(String contentExpression) {
        this.contentExpression = contentExpression;
    }

}
