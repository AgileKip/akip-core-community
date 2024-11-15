package org.akip.dao.filter;

import com.owse.searchFramework.ListFilter;

import javax.persistence.Query;

public class AssigneeAndCandidateGroupFilter extends ListFilter {

	private String type = "AssigneeAndCandidateGroupFilter";

	private String assignee;

	@Override
	public String buildCriteria(String searchField) {
		StringBuilder hql = new StringBuilder();
		hql.append("  ( ");
		hql.append("    entity.assignee = :assignee ");
		hql.append("    or ");
		hql.append("    entity.assignee is null ");
		hql.append("  ) ");
		hql.append(" and ");
		hql.append("  ( ");
		hql.append("    entity.computedCandidateGroups is null ");
		for (Object authority:getValues()) {
			hql.append(" or trim(lower( entity.computedCandidateGroups )) like '%," + authority.toString().toLowerCase() + ",%' ");
		}
        hql.append("  ) ");
		return hql.toString();
	}

	@Override
	public void setParameterInQuery(Query query) {
		query.setParameter("assignee", assignee);
	}

	@Override
	public String getType() {
		return type;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
}
