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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.isoft.tms.domain.EmployeeVacation} entity. This class is used
 * in {@link com.isoft.tms.web.rest.EmployeeVacationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-vacations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeVacationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateFrom;

    private LocalDateFilter dateTo;

    private InstantFilter timeFrom;

    private InstantFilter timeTo;

    private IntegerFilter status;

    private IntegerFilter type;

    private LongFilter employeeId;

    public EmployeeVacationCriteria() {
    }

    public EmployeeVacationCriteria(EmployeeVacationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateFrom = other.dateFrom == null ? null : other.dateFrom.copy();
        this.dateTo = other.dateTo == null ? null : other.dateTo.copy();
        this.timeFrom = other.timeFrom == null ? null : other.timeFrom.copy();
        this.timeTo = other.timeTo == null ? null : other.timeTo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EmployeeVacationCriteria copy() {
        return new EmployeeVacationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateFilter dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateFilter getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateFilter dateTo) {
        this.dateTo = dateTo;
    }

    public InstantFilter getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(InstantFilter timeFrom) {
        this.timeFrom = timeFrom;
    }

    public InstantFilter getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(InstantFilter timeTo) {
        this.timeTo = timeTo;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public IntegerFilter getType() {
        return type;
    }

    public void setType(IntegerFilter type) {
        this.type = type;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeVacationCriteria that = (EmployeeVacationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateFrom, that.dateFrom) &&
            Objects.equals(dateTo, that.dateTo) &&
            Objects.equals(timeFrom, that.timeFrom) &&
            Objects.equals(timeTo, that.timeTo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(type, that.type) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateFrom,
        dateTo,
        timeFrom,
        timeTo,
        status,
        type,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "EmployeeVacationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateFrom != null ? "dateFrom=" + dateFrom + ", " : "") +
                (dateTo != null ? "dateTo=" + dateTo + ", " : "") +
                (timeFrom != null ? "timeFrom=" + timeFrom + ", " : "") +
                (timeTo != null ? "timeTo=" + timeTo + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
