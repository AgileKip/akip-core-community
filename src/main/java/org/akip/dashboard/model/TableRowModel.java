package org.akip.dashboard.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TableRowModel {

    private String title;
    private List<BigDecimal> values = new ArrayList<>();
    private List<String> humanReadableValues = new ArrayList<>();

    public TableRowModel title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TableRowModel addValue(BigDecimal value) {
        getValues().add(value);
        return this;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }

    public TableRowModel addHumanReadableValue(String humanRadableValue) {
        getHumanReadableValues().add(humanRadableValue);
        return this;
    }

    public List<String> getHumanReadableValues() {
        return humanReadableValues;
    }

    public void setHumanReadableValues(List<String> humanReadableValues) {
        this.humanReadableValues = humanReadableValues;
    }
}
