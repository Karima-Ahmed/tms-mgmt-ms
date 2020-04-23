package com.isoft.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * WorkingDays (working_days) entity.
 */
@Entity
@Table(name = "working_days")
public class WorkingDays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @NotNull
    @Column(name = "name_ar", nullable = false)
    private String nameAr;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonIgnoreProperties("workingDays")
    private CenterWorkingDays centerWorkingDays;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public WorkingDays nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public WorkingDays nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public Boolean isStatus() {
        return status;
    }

    public WorkingDays status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CenterWorkingDays getCenterWorkingDays() {
        return centerWorkingDays;
    }

    public WorkingDays centerWorkingDays(CenterWorkingDays centerWorkingDays) {
        this.centerWorkingDays = centerWorkingDays;
        return this;
    }

    public void setCenterWorkingDays(CenterWorkingDays centerWorkingDays) {
        this.centerWorkingDays = centerWorkingDays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingDays)) {
            return false;
        }
        return id != null && id.equals(((WorkingDays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorkingDays{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
