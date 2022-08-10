package org.akip.dao.filter;

import com.owse.searchFramework.ListFilter;
import org.akip.domain.Tenant;

import javax.persistence.Query;

public class TenantFilter extends ListFilter {

	private String type = "Tenant";

	@Override
	public String buildCriteria(String searchField) {
		StringBuilder hql = new StringBuilder();
		hql.append(" ( ${searchField} is null ");
		for (Object value:getValues()) {
			Tenant tenant = (Tenant) value;
			hql.append(" or " + searchField + ".id = " + tenant.getId());
		}
		hql.append(" ) ");
		return hql.toString();
	}

	@Override
	public void setParameterInQuery(Query query) {
	}

	@Override
	public String getType() {
		return type;
	}
}
