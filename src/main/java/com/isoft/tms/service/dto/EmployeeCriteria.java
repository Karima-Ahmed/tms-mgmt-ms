package com.isoft.tms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.isoft.tms.domain.enumeration.Language;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.isoft.tms.domain.Employee} entity. This class is used
 * in {@link com.isoft.tms.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Language
     */
    public static class LanguageFilter extends Filter<Language> {

        public LanguageFilter() {
        }

        public LanguageFilter(LanguageFilter filter) {
            super(filter);
        }

        @Override
        public LanguageFilter copy() {
            return new LanguageFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullNameEn;

    private StringFilter fullNameAr;

    private LongFilter userId;

    private StringFilter email;

    private StringFilter mobileNo;

    private IntegerFilter status;

    private LanguageFilter langKey;

    private LongFilter employeeVacationsId;

    private LongFilter departmentId;

    private LongFilter centerId;

    private LongFilter employeeTypeId;

    public EmployeeCriteria() {
    }

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullNameEn = other.fullNameEn == null ? null : other.fullNameEn.copy();
        this.fullNameAr = other.fullNameAr == null ? null : other.fullNameAr.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.langKey = other.langKey == null ? null : other.langKey.copy();
        this.employeeVacationsId = other.employeeVacationsId == null ? null : other.employeeVacationsId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.centerId = other.centerId == null ? null : other.centerId.copy();
        this.employeeTypeId = other.employeeTypeId == null ? null : other.employeeTypeId.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(StringFilter fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public StringFilter getFullNameAr() {
        return fullNameAr;
    }

    public void setFullNameAr(StringFilter fullNameAr) {
        this.fullNameAr = fullNameAr;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public LanguageFilter getLangKey() {
        return langKey;
    }

    public void setLangKey(LanguageFilter langKey) {
        this.langKey = langKey;
    }

    public LongFilter getEmployeeVacationsId() {
        return employeeVacationsId;
    }

    public void setEmployeeVacationsId(LongFilter employeeVacationsId) {
        this.employeeVacationsId = employeeVacationsId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getCenterId() {
        return centerId;
    }

    public void setCenterId(LongFilter centerId) {
        this.centerId = centerId;
    }

    public LongFilter getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(LongFilter employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fullNameEn, that.fullNameEn) &&
            Objects.equals(fullNameAr, that.fullNameAr) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(employeeVacationsId, that.employeeVacationsId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(centerId, that.centerId) &&
            Objects.equals(employeeTypeId, that.employeeTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fullNameEn,
        fullNameAr,
        userId,
        email,
        mobileNo,
        status,
        langKey,
        employeeVacationsId,
        departmentId,
        centerId,
        employeeTypeId
        );
    }

    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fullNameEn != null ? "fullNameEn=" + fullNameEn + ", " : "") +
                (fullNameAr != null ? "fullNameAr=" + fullNameAr + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (langKey != null ? "langKey=" + langKey + ", " : "") +
                (employeeVacationsId != null ? "employeeVacationsId=" + employeeVacationsId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (centerId != null ? "centerId=" + centerId + ", " : "") +
                (employeeTypeId != null ? "employeeTypeId=" + employeeTypeId + ", " : "") +
            "}";
    }

}
