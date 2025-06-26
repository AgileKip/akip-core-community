package org.akip.domainEntity.filters;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DomainEntitySearchDateFilter extends DomainEntitySearchFilter {

    private String id;

    private Date value1;

    private Date value2;

    private DomainEntitySearchDateFilterOperator operator;

    @Override
    public boolean isActive() {
        if (operator == null) {
            return false;
        }
        switch (operator) {
            case TODAY:
            case YESTERDAY:
            case THIS_WEEK:
            case LAST_WEEK:
            case THIS_MONTH:
            case LAST_MONTH:
            case THIS_YEAR:
            case LAST_YEAR:
                return true;
            case AFTER_THAT:
            case BEFORE_THAT:
                return value1 != null;
            case BETWEEN:
                return value1 != null && value2 != null;
        }
        return false;
    }

    @Override
    public void buildCriteria(DomainEntityDefinitionDTO domainEntityDefinition, StringBuilder stringBuilder) {
        switch (operator) {
            case TODAY:
            case YESTERDAY:
                stringBuilder.append(" AND ")
                        .append(domainEntityDefinition.getName())
                        .append(".")
                        .append(getId())
                        .append(" = :")
                        .append(getId() + "1 ");
                return;
            case THIS_WEEK:
            case LAST_WEEK:
            case THIS_MONTH:
            case LAST_MONTH:
            case THIS_YEAR:
            case LAST_YEAR:
            case BETWEEN:
                stringBuilder.append(" AND ")
                        .append(domainEntityDefinition.getName())
                        .append(".")
                        .append(getId())
                        .append(" >= :")
                        .append(getId() + "1")
                        .append(" AND ")
                        .append(domainEntityDefinition.getName())
                        .append(".")
                        .append(getId())
                        .append(" <= :")
                        .append(getId() + "2 ");
                return;
            case AFTER_THAT:
                stringBuilder.append(" AND ")
                        .append(domainEntityDefinition.getName())
                        .append(".")
                        .append(getId())
                        .append(" >= :")
                        .append(getId() + "1 ");
                return;
            case BEFORE_THAT:
                stringBuilder.append(" AND ")
                        .append(domainEntityDefinition.getName())
                        .append(".")
                        .append(getId())
                        .append(" <= :")
                        .append(getId() + "1 ");
        }
    }

    @Override
    public void setParameters(MapSqlParameterSource params) {
        LocalDate today = LocalDate.now();

        if (operator == DomainEntitySearchDateFilterOperator.TODAY) {
            params.addValue(getId() + "1", today);
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.YESTERDAY) {
            params.addValue(getId() + "1", LocalDate.now().minusDays(1));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.THIS_WEEK) {
            params.addValue(getId() + "1", today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
            params.addValue(getId() + "2", today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.LAST_WEEK) {
            params.addValue(getId() + "1", today.minusDays(7).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
            params.addValue(getId() + "2", today.minusDays(7).with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.THIS_MONTH) {
            params.addValue(getId() + "1", today.with(TemporalAdjusters.firstDayOfMonth()));
            params.addValue(getId() + "2", today.with(TemporalAdjusters.lastDayOfMonth()));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.LAST_MONTH) {
            params.addValue(getId() + "1", today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));
            params.addValue(getId() + "2", today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.THIS_YEAR) {
            params.addValue(getId() + "1", today.with(TemporalAdjusters.firstDayOfYear()));
            params.addValue(getId() + "2", today.with(TemporalAdjusters.lastDayOfYear()));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.LAST_YEAR) {
            params.addValue(getId() + "1", today.minusYears(1).with(TemporalAdjusters.firstDayOfYear()));
            params.addValue(getId() + "2", today.minusYears(1).with(TemporalAdjusters.lastDayOfYear()));
            return;
        }

        if (operator == DomainEntitySearchDateFilterOperator.BEFORE_THAT || operator == DomainEntitySearchDateFilterOperator.AFTER_THAT) {
            params.addValue(getId() + "1", value1);
            return;
        }

        params.addValue(getId() + "1", value1);
        params.addValue(getId() + "2", value2);
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return "date";
    }

    public Date getValue1() {
        return value1;
    }

    public void setValue1(Date value1) {
        this.value1 = value1;
    }

    public Date getValue2() {
        return value2;
    }

    public void setValue2(Date value2) {
        this.value2 = value2;
    }

    public DomainEntitySearchDateFilterOperator[] getOperators() {
        return DomainEntitySearchDateFilterOperator.values();
    }

    public DomainEntitySearchDateFilterOperator getOperator() {
        return operator;
    }

    public void setOperator(DomainEntitySearchDateFilterOperator operator) {
        this.operator = operator;
    }

    public DomainEntitySearchDateFilter id(String id) {
        setId(id);
        return this;
    }

    public DomainEntitySearchDateFilter value1(Date value1) {
        this.value1 = value1;
        return this;
    }

    public DomainEntitySearchDateFilter value2(Date value2) {
        this.value2 = value2;
        return this;
    }

    public DomainEntitySearchDateFilter operator(DomainEntitySearchDateFilterOperator operator) {
        this.operator = operator;
        return this;
    }
}
