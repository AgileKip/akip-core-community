package org.akip.dashboard.model;

public class GroupConfigModel {

    private String title;
    private String type;
    private String expression;
    private String groupBuilder;

    public GroupConfigModel title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GroupConfigModel type(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GroupConfigModel expression(String expression) {
        this.expression = expression;
        return this;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public GroupConfigModel groupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
        return this;
    }

    public String getGroupBuilder() {
        return groupBuilder;
    }

    public void setGroupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
    }
}
