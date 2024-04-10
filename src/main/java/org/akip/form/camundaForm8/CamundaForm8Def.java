package org.akip.form.camundaForm8;

import java.util.List;

public class CamundaForm8Def {

    private String id;

    private String type;

    private String executionPlatform;

    private String executionPlatformVersion;

    private CamundaForm8ExporterDef exporter;

    private String schemaVersion;

    private List<CamundaForm8ComponentDef> components;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExecutionPlatform() {
        return executionPlatform;
    }

    public void setExecutionPlatform(String executionPlatform) {
        this.executionPlatform = executionPlatform;
    }

    public String getExecutionPlatformVersion() {
        return executionPlatformVersion;
    }

    public void setExecutionPlatformVersion(String executionPlatformVersion) {
        this.executionPlatformVersion = executionPlatformVersion;
    }

    public CamundaForm8ExporterDef getExporter() {
        return exporter;
    }

    public void setExporter(CamundaForm8ExporterDef exporter) {
        this.exporter = exporter;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public List<CamundaForm8ComponentDef> getComponents() {
        return components;
    }

    public void setComponents(List<CamundaForm8ComponentDef> components) {
        this.components = components;
    }
}
