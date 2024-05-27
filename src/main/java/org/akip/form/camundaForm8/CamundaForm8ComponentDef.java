package org.akip.form.camundaForm8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CamundaForm8ComponentDef {

    private String id;

    private String key;

    private String label;

    private String type;

    private String subtype;

    private CamundaForm8LayoutDef layout;

    private String dateLabel;

    private boolean disallowPassedDates;

    private boolean showOutline;

    private Long decimalDigits;

    private String increment;

    private boolean serializeToString;

    private List<CamundaForm8ValueDef> values;

    private Object defaultValue;

    private boolean disabled;

    private boolean readonly;

    private boolean searchable;

    private String alt;

    private String source;

    private Long height;

    private CamundaForm8ConditionalDef conditional;

    private CamundaForm8ValidateDef validate;

    private Map<String, String> properties = new HashMap<>();

    private CamundaForm8AppearanceDef appearance;

    private String description;

    private String text;

    private String url;

    private String path;

    private List<CamundaForm8ComponentDef> components = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public CamundaForm8LayoutDef getLayout() {
        return layout;
    }

    public void setLayout(CamundaForm8LayoutDef layout) {
        this.layout = layout;
    }

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public boolean isDisallowPassedDates() {
        return disallowPassedDates;
    }

    public void setDisallowPassedDates(boolean disallowPassedDates) {
        this.disallowPassedDates = disallowPassedDates;
    }

    public boolean isShowOutline() {
        return showOutline;
    }

    public void setShowOutline(boolean showOutline) {
        this.showOutline = showOutline;
    }

    public Long getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Long decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public boolean isSerializeToString() {
        return serializeToString;
    }

    public void setSerializeToString(boolean serializeToString) {
        this.serializeToString = serializeToString;
    }

    public List<CamundaForm8ValueDef> getValues() {
        return values;
    }

    public void setValues(List<CamundaForm8ValueDef> values) {
        this.values = values;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public CamundaForm8ConditionalDef getConditional() {
        return conditional;
    }

    public void setConditional(CamundaForm8ConditionalDef conditional) {
        this.conditional = conditional;
    }

    public CamundaForm8ValidateDef getValidate() {
        return validate;
    }

    public void setValidate(CamundaForm8ValidateDef validate) {
        this.validate = validate;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public CamundaForm8AppearanceDef getAppearance() {
        return appearance;
    }

    public void setAppearance(CamundaForm8AppearanceDef appearance) {
        this.appearance = appearance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<CamundaForm8ComponentDef> getComponents() {
        return components;
    }

    public void setComponents(List<CamundaForm8ComponentDef> components) {
        this.components = components;
    }
}
