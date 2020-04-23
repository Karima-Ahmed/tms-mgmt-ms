package com.isoft.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;

/**
 * EmployeeVacation (employee_vacation) entity.
 */
@Entity
@Table(name = "employee_vacation")
public class EmployeeVacation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @NotNull
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Column(name = "time_from")
    private Instant timeFrom;

    @Column(name = "time_to")
    private Instant timeTo;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @ManyToOne
    @JsonIgnoreProperties("employeeVacations")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public EmployeeVacation dateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public EmployeeVacation dateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public Instant getTimeFrom() {
        return timeFrom;
    }

    public EmployeeVacation timeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(Instant timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Instant getTimeTo() {
        return timeTo;
    }

    public EmployeeVacation timeTo(Instant timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(Instant timeTo) {
        this.timeTo = timeTo;
    }

    public Integer getStatus() {
        return status;
    }

    public EmployeeVacation status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public EmployeeVacation type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeVacation employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeVacation)) {
            return false;
        }
        return id != null && id.equals(((EmployeeVacation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmployeeVacation{" +
            "id=" + getId() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", timeFrom='" + getTimeFrom() + "'" +
            ", timeTo='" + getTimeTo() + "'" +
            ", status=" + getStatus() +
            ", type=" + getType() +
            "}";
    }
}
