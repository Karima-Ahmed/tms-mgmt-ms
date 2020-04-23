package com.isoft.tms.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.tms.domain.Center} entity.
 */
@ApiModel(description = "Center (center) entity.")
public class CenterDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nameEn;

    @NotNull
    private String nameAr;

    private String address;

    private Double longitude;

    private Double latitude;

    private Instant startWorkingHour;

    private Instant endWorkingHour;

    private String phoneNo;

    private String mobileNo;

    
    private String email;

    private String country;

    private String city;

    private String imageUrl;

    private Integer status;


    private Long centerWorkingDaysId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCenterWorkingDaysId() {
        return centerWorkingDaysId;
    }

    public void setCenterWorkingDaysId(Long centerWorkingDaysId) {
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

        CenterDTO centerDTO = (CenterDTO) o;
        if (centerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CenterDTO{" +
            "id=" + getId() +
            ", nameEn='" + getNameEn() + "'" +
            ", nameAr='" + getNameAr() + "'" +
            ", address='" + getAddress() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", startWorkingHour='" + getStartWorkingHour() + "'" +
            ", endWorkingHour='" + getEndWorkingHour() + "'" +
            ", phoneNo='" + getPhoneNo() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", status=" + getStatus() +
            ", centerWorkingDaysId=" + getCenterWorkingDaysId() +
            "}";
    }
}
