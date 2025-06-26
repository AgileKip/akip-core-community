package org.akip.domainEntity;

import org.akip.domain.DomainEntityDefinition;

import java.util.List;
import java.util.Map;

public class DomainEntitySearchResult {

    private DomainEntityDefinition domainEntityDefinition;
    private int total;
    private List<Map<String,Object>> results;

    public DomainEntityDefinition getDomainEntityDefinition() {
        return domainEntityDefinition;
    }

    public void setDomainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, Object>> results) {
        this.results = results;
    }
}
