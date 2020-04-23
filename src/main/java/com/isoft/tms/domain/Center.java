package com.isoft.tms.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * Center (center) entity.
 */
@Entity
@Table(name = "center")
public class Center implements Serializable {

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

    @Column(name = "address")
    private String address;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "start_working_hour")
    private Instant startWorkingHour;

    @Column(name = "end_working_hour")
    private Instant endWorkingHour;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "mobile_no")
    private String mobileNo;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private Integer status;

    @OneToOne
    @JoinColumn(unique = true)
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

    public Center nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameAr() {
        return nameAr;
    }

    public Center nameAr(String nameAr) {
        this.nameAr = nameAr;
        return this;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getAddress() {
        return address;
    }

    public Center address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Center longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Center latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Instant getStartWorkingHour() {
        return startWorkingHour;
    }

    public Center startWorkingHour(Instant startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
        return this;
    }

    public void setStartWorkingHour(Instant startWorkingHour) {
        this.startWorkingHour = startWorkingHour;
    }

    public Instant getEndWorkingHour() {
        return endWorkingHour;
    }

    public Center endWorkingHour(Instant endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
        return this;
    }

    public void setEndWorkingHour(Instant endWorkingHour) {
        this.endWorkingHour = endWorkingHour;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Center phoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        return this;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public Center mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public Center email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public Center country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Center city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Center imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public Center status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CenterWorkingDays getCenterWorkingDays() {
        return centerWorkingDays;
    }

    public Center centerWorkingDays(CenterWorkingDays centerWorkingDays) {
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
        if (!(o instanceof Center)) {
            return false;
        }
        return id != null && id.equals(((Center) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Center{" +
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
            "}";
    }
}
