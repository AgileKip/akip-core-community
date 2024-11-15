package org.akip.service.dto;

public class ProcessTimelineItemDefinitionDTO {

    private Long id;

    private int step;

    private String name;

    private String checkStatusExpression;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public ProcessTimelineItemDefinitionDTO step(int step) {
        this.step = step;
        return this;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public ProcessTimelineItemDefinitionDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckStatusExpression() {
        return checkStatusExpression;
    }

    public ProcessTimelineItemDefinitionDTO checkStatusExpression(String checkStatusExpression) {
        this.checkStatusExpression = checkStatusExpression;
        return this;
    }

    public void setCheckStatusExpression(String checkStatusExpression) {
        this.checkStatusExpression = checkStatusExpression;
    }
}
