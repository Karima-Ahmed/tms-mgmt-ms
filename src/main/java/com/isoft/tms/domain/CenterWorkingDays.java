package com.isoft.tms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * CenterWorkingDays (center_working_days) entity.
 */
@Entity
@Table(name = "center_working_days")
public class CenterWorkingDays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_working_hour")
    private Instant startWorkingHour;

    @Column(name = "end_working_hour")
    private Instant endWorkingHour;

    @OneToMany(mappedBy = "centerWorkingDays")
    private Set<WorkingDays> workingDays = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartWorkingHour() {
        return startWorkingHour;
    }

    public CenterWorkingDays startWorkingHour(Instant startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
        return this;
    }

    public void setStartWorkingHour(Instant startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public Instant getEndWorkingHour() {
        return endWorkingHour;
    }

    public CenterWorkingDays endWorkingHour(Instant endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
        return this;
    }

    public void setEndWorkingHour(Instant endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    public Set<WorkingDays> getWorkingDays() {
        return workingDays;
    }

    public CenterWorkingDays workingDays(Set<WorkingDays> workingDays) {
        this.workingDays = workingDays;
        return this;
    }

    public CenterWorkingDays addWorkingDays(WorkingDays workingDays) {
        this.workingDays.add(workingDays);
        workingDays.setCenterWorkingDays(this);
        return this;
    }

    public CenterWorkingDays removeWorkingDays(WorkingDays workingDays) {
        this.workingDays.remove(workingDays);
        workingDays.setCenterWorkingDays(null);
        return this;
    }

    public void setWorkingDays(Set<WorkingDays> workingDays) {
        this.workingDays = workingDays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterWorkingDays)) {
            return false;
        }
        return id != null && id.equals(((CenterWorkingDays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CenterWorkingDays{" +
            "id=" + getId() +
            ", startWorkingHour='" + getStartWorkingHour() + "'" +
            ", endWorkingHour='" + getEndWorkingHour() + "'" +
            "}";
    }
}
