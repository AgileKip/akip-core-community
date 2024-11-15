package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A TenantMember.
 */
@Entity
@Table(name = "tenant_member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TenantMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    private Tenant tenant;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "tenant_member_tenant_role",
            joinColumns = { @JoinColumn(name = "tenant_member_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "tenant_role_id", referencedColumnName = "id") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private List<TenantRole> tenantRoles = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TenantMember id(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public TenantMember username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public TenantMember tenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public List<TenantRole> getTenantRoles() {
        return tenantRoles;
    }

    public TenantMember tenantRoles(List<TenantRole> tenantRoles) {
        this.tenantRoles = tenantRoles;
        return this;
    }

    public void setTenantRoles(List<TenantRole> tenantRoles) {
        this.tenantRoles = tenantRoles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantMember)) {
            return false;
        }
        return id != null && id.equals(((TenantMember) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TentantUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", tenant='" + getTenant() + "'" +
            "}";
    }
}
