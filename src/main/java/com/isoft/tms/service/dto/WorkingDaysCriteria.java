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

/**
 * Criteria class for the {@link com.isoft.tms.domain.WorkingDays} entity. This class is used
 * in {@link com.isoft.tms.web.rest.WorkingDaysResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /working-days?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkingDaysCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameEn;

    private StringFilter nameAr;

    private BooleanFilter status;

    private LongFilter centerWorkingDaysId;

    public WorkingDaysCriteria() {
    }

    public WorkingDaysCriteria(WorkingDaysCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.centerWorkingDaysId = other.centerWorkingDaysId == null ? null : other.centerWorkingDaysId.copy();
    }

    @Override
    public WorkingDaysCriteria copy() {
        return new WorkingDaysCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNameEn() {
        return nameEn;
    }

    public void setNameEn(StringFilter nameEn) {
        this.nameEn = nameEn;
    }

    public StringFilter getNameAr() {
        return nameAr;
    }

    public void setNameAr(StringFilter nameAr) {
        this.nameAr = nameAr;
    }

    public BooleanFilter getStatus() {
        return status;
    }

    public void setStatus(BooleanFilter status) {
        this.status = status;
    }

    public LongFilter getCenterWorkingDaysId() {
        return centerWorkingDaysId;
    }

    public void setCenterWorkingDaysId(LongFilter centerWorkingDaysId) {
        this.centerWorkingDaysId = centerWorkingDaysId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkingDaysCriteria that = (WorkingDaysCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(status, that.status) &&
            Objects.equals(centerWorkingDaysId, that.centerWorkingDaysId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameEn,
        nameAr,
        status,
        centerWorkingDaysId
        );
    }

    @Override
    public String toString() {
        return "WorkingDaysCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
                (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (centerWorkingDaysId != null ? "centerWorkingDaysId=" + centerWorkingDaysId + ", " : "") +
            "}";
    }

}
