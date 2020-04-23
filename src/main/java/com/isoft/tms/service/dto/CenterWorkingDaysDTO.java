package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.CenterWorkingDays} entity.
 */
@ApiModel(description = "CenterWorkingDays (center_working_days) entity.")
public class CenterWorkingDaysDTO implements Serializable {
    
    private Long id;

    private Instant startWorkingHour;

    private Instant endWorkingHour;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartWorkingHour() {
        return startWorkingHour;
    }

    public void setStartWorkingHour(Instant startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public Instant getEndWorkingHour() {
        return endWorkingHour;
    }

    public void setEndWorkingHour(Instant endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CenterWorkingDaysDTO centerWorkingDaysDTO = (CenterWorkingDaysDTO) o;
        if (centerWorkingDaysDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centerWorkingDaysDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CenterWorkingDaysDTO{" +
            "id=" + getId() +
            ", startWorkingHour='" + getStartWorkingHour() + "'" +
            ", endWorkingHour='" + getEndWorkingHour() + "'" +
            "}";
    }
}
