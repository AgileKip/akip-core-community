package org.akip.service.dto;

import java.util.List;

public class Filter {

    private String id;

    private String label;

    private String type;

    private String value;
    private String operator;

    private String filterEntity;

    private List<Object> options;

    private boolean show;

    public Filter() {}

    public Filter(Filter filter) {
        this.id = filter.id;
        this.label = filter.label;
        this.type = filter.type;
        this.value = filter.value;
        this.filterEntity = filter.filterEntity;
        this.options = filter.options;
        this.show = filter.show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFilterEntity() {
        return filterEntity;
    }

    public void setFilterEntity(String filterEntity) {
        this.filterEntity = filterEntity;
    }

    public List<Object> getOptions() {
        return options;
    }

    public void setOptions(List<Object> options) {
        this.options = options;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return (
            "Filter{" +
            "id='" +
            id +
            '\'' +
            ", label='" +
            label +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", value='" +
            value +
            '\'' +
            ", filterEntity='" +
            filterEntity +
            '\'' +
            ", options=" +
            options +
            ", show=" +
            show +
            '}'
        );
    }
}
