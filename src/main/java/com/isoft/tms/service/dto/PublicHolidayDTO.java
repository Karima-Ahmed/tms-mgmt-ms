package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.PublicHoliday} entity.
 */
@ApiModel(description = "PublicHoliday (public_holiday) entity.")
public class PublicHolidayDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String description;

    private Integer year;

    private Integer type;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
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

        PublicHolidayDTO publicHolidayDTO = (PublicHolidayDTO) o;
        if (publicHolidayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publicHolidayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublicHolidayDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", year=" + getYear() +
            ", type=" + getType() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            "}";
    }
}
