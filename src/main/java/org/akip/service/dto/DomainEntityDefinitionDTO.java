package org.akip.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class DomainEntityDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityDefinitionDTO)) {
            return false;
        }

        DomainEntityDefinitionDTO domainEntityDefinitionDTO = (DomainEntityDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, domainEntityDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "DomainEntityDefinitionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", version='" + version + '\'' +
            '}';
    }
}
