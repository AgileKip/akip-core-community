package org.akip.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.FormDefinition} entity.
 */
public class FormDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private String formBuilder;

    private String formVersion;

    private String formSchema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormBuilder() {
        return formBuilder;
    }

    public void setFormBuilder(String formBuilder) {
        this.formBuilder = formBuilder;
    }

    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }

    public String getFormSchema() {
        return formSchema;
    }

    public void setFormSchema(String formSchema) {
        this.formSchema = formSchema;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormDefinitionDTO that = (FormDefinitionDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FormDefinitionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
