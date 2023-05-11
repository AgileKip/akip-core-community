package org.akip.dao.filter;

import com.owse.searchFramework.ListFilter;

import javax.persistence.Query;

public class AuthorityFilter extends ListFilter {

	private String type = "Authority";

	@Override
	public String buildCriteria(String searchField) {
		StringBuilder hql = new StringBuilder();
		hql.append(" ( " + searchField + " is null ");
		for (Object authority:getValues()) {
			hql.append(" or trim(lower(" + searchField + " )) like '%," + authority.toString().toLowerCase() + ",%' ");
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
