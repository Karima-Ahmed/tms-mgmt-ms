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

/**
 * Criteria class for the {@link com.isoft.tms.domain.Center} entity. This class is used
 * in {@link com.isoft.tms.web.rest.CenterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /centers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CenterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nameEn;

    private StringFilter nameAr;

    private StringFilter address;

    private DoubleFilter longitude;

    private DoubleFilter latitude;

    private InstantFilter startWorkingHour;

    private InstantFilter endWorkingHour;

    private StringFilter phoneNo;

    private StringFilter mobileNo;

    private StringFilter email;

    private StringFilter country;

    private StringFilter city;

    private StringFilter imageUrl;

    private IntegerFilter status;

    private LongFilter centerWorkingDaysId;

    public CenterCriteria() {
    }

    public CenterCriteria(CenterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.nameAr = other.nameAr == null ? null : other.nameAr.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.startWorkingHour = other.startWorkingHour == null ? null : other.startWorkingHour.copy();
        this.endWorkingHour = other.endWorkingHour == null ? null : other.endWorkingHour.copy();
        this.phoneNo = other.phoneNo == null ? null : other.phoneNo.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.centerWorkingDaysId = other.centerWorkingDaysId == null ? null : other.centerWorkingDaysId.copy();
    }

    @Override
    public CenterCriteria copy() {
        return new CenterCriteria(this);
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

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public InstantFilter getStartWorkingHour() {
        return startWorkingHour;
    }

    public void setStartWorkingHour(InstantFilter startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public InstantFilter getEndWorkingHour() {
        return endWorkingHour;
    }

    public void setEndWorkingHour(InstantFilter endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    public StringFilter getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(StringFilter phoneNo) {
        this.phoneNo = phoneNo;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getCountry() {
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
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
        final CenterCriteria that = (CenterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(nameAr, that.nameAr) &&
            Objects.equals(address, that.address) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(startWorkingHour, that.startWorkingHour) &&
            Objects.equals(endWorkingHour, that.endWorkingHour) &&
            Objects.equals(phoneNo, that.phoneNo) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(email, that.email) &&
            Objects.equals(country, that.country) &&
            Objects.equals(city, that.city) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(centerWorkingDaysId, that.centerWorkingDaysId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nameEn,
        nameAr,
        address,
        longitude,
        latitude,
        startWorkingHour,
        endWorkingHour,
        phoneNo,
        mobileNo,
        email,
        country,
        city,
        imageUrl,
        status,
        centerWorkingDaysId
        );
    }

    @Override
    public String toString() {
        return "CenterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
                (nameAr != null ? "nameAr=" + nameAr + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (startWorkingHour != null ? "startWorkingHour=" + startWorkingHour + ", " : "") +
                (endWorkingHour != null ? "endWorkingHour=" + endWorkingHour + ", " : "") +
                (phoneNo != null ? "phoneNo=" + phoneNo + ", " : "") +
                (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (country != null ? "country=" + country + ", " : "") +
                (city != null ? "city=" + city + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (centerWorkingDaysId != null ? "centerWorkingDaysId=" + centerWorkingDaysId + ", " : "") +
            "}";
    }

}
