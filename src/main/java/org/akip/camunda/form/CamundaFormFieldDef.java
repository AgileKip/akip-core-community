package org.akip.camunda.form;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamundaFormFieldDef {

    protected String id;
    protected String label;
    protected String type;
    protected Object defaultValue;
    protected List<CamundaFormFieldValidationConstraintDef> validationConstraints = new ArrayList();
    protected Map<String, String> values = new HashMap();
    protected Map<String, String> properties = new HashMap();

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

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<CamundaFormFieldValidationConstraintDef> getValidationConstraints() {
        return validationConstraints;
    }

    public void setValidationConstraints(List<CamundaFormFieldValidationConstraintDef> validationConstraints) {
        this.validationConstraints = validationConstraints;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
