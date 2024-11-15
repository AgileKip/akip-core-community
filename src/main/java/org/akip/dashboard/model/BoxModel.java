package org.akip.dashboard.model;

public class BoxModel {

    private String title;
    private String subtitle;
    private String value;
    private String variant = "primary";

    public BoxModel title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String label) {
        this.title = label;
    }

    public BoxModel subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public BoxModel value(String value) {
        this.value = value;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BoxModel variant(String variant) {
        this.variant = variant;
        return this;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }
}
