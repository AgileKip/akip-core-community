package org.akip.form.camundaForm7;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamundaForm7FieldDef {

    protected String id;
    protected String label;
    protected String type;
    protected Object defaultValue;
    protected List<CamundaForm7FieldValidationConstraintDef> validationConstraints = new ArrayList();
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

    public List<CamundaForm7FieldValidationConstraintDef> getValidationConstraints() {
        return validationConstraints;
    }

    public void setValidationConstraints(List<CamundaForm7FieldValidationConstraintDef> validationConstraints) {
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
