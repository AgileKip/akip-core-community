package org.akip.service.dto;

import org.akip.domain.Tenant;

import java.io.Serializable;
import java.util.Objects;

public class TenantRoleDTO implements Serializable {

    private Long id;

    private String name;

    private Tenant tenant;

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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantRoleDTO that = (TenantRoleDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TenantRoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
