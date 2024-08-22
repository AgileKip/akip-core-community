package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "form_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FormDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "form_builder")
    private String formBuilder;

    @Column(name = "form_version")
    private String formVersion;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "form_schema")
    private String formSchema;

    public FormDefinition(){}


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
        FormDefinition that = (FormDefinition) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FormDefinition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
