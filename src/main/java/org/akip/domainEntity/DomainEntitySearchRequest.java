package org.akip.domainEntity;

import org.akip.domainEntity.filters.DomainEntitySearchFilter;

import java.util.ArrayList;
import java.util.List;

public class DomainEntitySearchRequest {

    private List<DomainEntitySearchFilter> filters = new ArrayList<>();

    public List<DomainEntitySearchFilter> getFilters() {
        return filters;
    }

    public DomainEntitySearchRequest addFilter(DomainEntitySearchFilter filter) {
        filters.add(filter);
        return this;
    }
}
