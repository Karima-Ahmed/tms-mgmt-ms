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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.isoft.tms.domain.PublicHoliday} entity. This class is used
 * in {@link com.isoft.tms.web.rest.PublicHolidayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /public-holidays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PublicHolidayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private IntegerFilter year;

    private IntegerFilter type;

    private LocalDateFilter dateFrom;

    private LocalDateFilter dateTo;

    public PublicHolidayCriteria() {
    }

    public PublicHolidayCriteria(PublicHolidayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.dateFrom = other.dateFrom == null ? null : other.dateFrom.copy();
        this.dateTo = other.dateTo == null ? null : other.dateTo.copy();
    }

    @Override
    public PublicHolidayCriteria copy() {
        return new PublicHolidayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public IntegerFilter getType() {
        return type;
    }

    public void setType(IntegerFilter type) {
        this.type = type;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PublicHolidayCriteria that = (PublicHolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(year, that.year) &&
            Objects.equals(type, that.type) &&
            Objects.equals(dateFrom, that.dateFrom) &&
            Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        year,
        type,
        dateFrom,
        dateTo
        );
    }

    @Override
    public String toString() {
        return "PublicHolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (dateFrom != null ? "dateFrom=" + dateFrom + ", " : "") +
                (dateTo != null ? "dateTo=" + dateTo + ", " : "") +
            "}";
    }

}
