package com.isoft.tms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.isoft.tms.domain.CenterWorkingDays} entity. This class is used
 * in {@link com.isoft.tms.web.rest.CenterWorkingDaysResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /center-working-days?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CenterWorkingDaysCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter startWorkingHour;

    private InstantFilter endWorkingHour;

    private LongFilter workingDaysId;

    public CenterWorkingDaysCriteria() {
    }

    public CenterWorkingDaysCriteria(CenterWorkingDaysCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startWorkingHour = other.startWorkingHour == null ? null : other.startWorkingHour.copy();
        this.endWorkingHour = other.endWorkingHour == null ? null : other.endWorkingHour.copy();
        this.workingDaysId = other.workingDaysId == null ? null : other.workingDaysId.copy();
    }

    @Override
    public CenterWorkingDaysCriteria copy() {
        return new CenterWorkingDaysCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getStartWorkingHour() {
        return startWorkingHour;
    }

    public void setStartWorkingHour(InstantFilter startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public InstantFilter getEndWorkingHour() {
        return endWorkingHour;
    }

    public void setEndWorkingHour(InstantFilter endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    public LongFilter getWorkingDaysId() {
        return workingDaysId;
    }

    public void setWorkingDaysId(LongFilter workingDaysId) {
        this.workingDaysId = workingDaysId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CenterWorkingDaysCriteria that = (CenterWorkingDaysCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startWorkingHour, that.startWorkingHour) &&
            Objects.equals(endWorkingHour, that.endWorkingHour) &&
            Objects.equals(workingDaysId, that.workingDaysId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startWorkingHour,
        endWorkingHour,
        workingDaysId
        );
    }

    @Override
    public String toString() {
        return "CenterWorkingDaysCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startWorkingHour != null ? "startWorkingHour=" + startWorkingHour + ", " : "") +
                (endWorkingHour != null ? "endWorkingHour=" + endWorkingHour + ", " : "") +
                (workingDaysId != null ? "workingDaysId=" + workingDaysId + ", " : "") +
            "}";
    }

}
