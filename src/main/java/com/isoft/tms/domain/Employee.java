package com.isoft.tms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import com.isoft.tms.domain.enumeration.Language;

/**
 * Employee (employee) entity.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "full_name_en", nullable = false)
    private String fullNameEn;

    @NotNull
    @Column(name = "full_name_ar", nullable = false)
    private String fullNameAr;

    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(name = "status")
    private Integer status;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang_key")
    private Language langKey;

    @OneToMany(mappedBy = "employee")
    private Set<EmployeeVacation> employeeVacations = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Center center;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private EmployeeType employeeType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public Employee fullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
        return this;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getFullNameAr() {
        return fullNameAr;
    }

    public Employee fullNameAr(String fullNameAr) {
        this.fullNameAr = fullNameAr;
        return this;
    }

    public void setFullNameAr(String fullNameAr) {
        this.fullNameAr = fullNameAr;
    }

    public Long getUserId() {
        return userId;
    }

    public Employee userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public Employee mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getStatus() {
        return status;
    }

    public Employee status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Language getLangKey() {
        return langKey;
    }

    public Employee langKey(Language langKey) {
        this.langKey = langKey;
        return this;
    }

    public void setLangKey(Language langKey) {
        this.langKey = langKey;
    }

    public Set<EmployeeVacation> getEmployeeVacations() {
        return employeeVacations;
    }

    public Employee employeeVacations(Set<EmployeeVacation> employeeVacations) {
        this.employeeVacations = employeeVacations;
        return this;
    }

    public Employee addEmployeeVacations(EmployeeVacation employeeVacation) {
        this.employeeVacations.add(employeeVacation);
        employeeVacation.setEmployee(this);
        return this;
    }

    public Employee removeEmployeeVacations(EmployeeVacation employeeVacation) {
        this.employeeVacations.remove(employeeVacation);
        employeeVacation.setEmployee(null);
        return this;
    }

    public void setEmployeeVacations(Set<EmployeeVacation> employeeVacations) {
        this.employeeVacations = employeeVacations;
    }

    public Department getDepartment() {
        return department;
    }

    public Employee department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Center getCenter() {
        return center;
    }

    public Employee center(Center center) {
        this.center = center;
        return this;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public Employee employeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", fullNameEn='" + getFullNameEn() + "'" +
            ", fullNameAr='" + getFullNameAr() + "'" +
            ", userId=" + getUserId() +
            ", email='" + getEmail() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", status=" + getStatus() +
            ", langKey='" + getLangKey() + "'" +
            "}";
    }
}
