package org.akip.service.dto;

import org.akip.domain.TenantRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.TenantMember} entity.
 */
public class TenantMemberDTO implements Serializable {

    private Long id;

    private String username;

    private TenantDTO tenant;

    private List<TenantRole> tenantRoles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public List<TenantRole> getTenantRoles() {
        return tenantRoles;
    }

    public void setTenantRoles(List<TenantRole> tenantRoles) {
        this.tenantRoles = tenantRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantMemberDTO)) {
            return false;
        }

        TenantMemberDTO tenantMemberDTO = (TenantMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tenantMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantMemberDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", tenant='" + getTenant() + "'" +
            "}";
    }
}
