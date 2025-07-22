package org.akip.dao.filter;

import com.owse.searchFramework.ListFilter;

import jakarta.persistence.Query;

public class AssigneeAndCandidateGroupFilter extends ListFilter {

	private String type = "AssigneeAndCandidateGroupFilter";

	private String assignee;

	@Override
	public String buildCriteria(String searchField) {
		StringBuilder hql = new StringBuilder();
		hql.append(" ( ");
		hql.append(" entity.assignee = :assignee ");
		hql.append(" or ");
		hql.append(" ( ");
		for (int i = 0; i < getValues().size(); i++) {
			Object authority = getValues().get(i);
			hql.append(" trim(lower(entity.computedCandidateGroups)) like '%," + authority.toString().toLowerCase() + ",%'");
			if (i < getValues().size() - 1) {
				hql.append(" or ");
			}
		}
		hql.append(" and ");
		hql.append(" trim(lower(entity.computedCandidateGroups)) like '%," + getValues().get(getValues().size() - 1).toString().toLowerCase() + ",%'");
		hql.append(" and ");
		hql.append(" entity.candidateUsers is null");
		hql.append("  ) ");
		hql.append(" or ");
		hql.append(" ( ");
		hql.append(" trim(lower( entity.candidateUsers )) like '%," + assignee.toLowerCase() + ",%'");
		hql.append(" and ");
		hql.append(" entity.candidateGroups is null");
		hql.append("  ) ");
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
