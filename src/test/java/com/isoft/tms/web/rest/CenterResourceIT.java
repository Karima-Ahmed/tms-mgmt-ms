package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.Center;
import com.isoft.tms.domain.CenterWorkingDays;
import com.isoft.tms.repository.CenterRepository;
import com.isoft.tms.service.CenterService;
import com.isoft.tms.service.dto.CenterDTO;
import com.isoft.tms.service.mapper.CenterMapper;
import com.isoft.tms.service.dto.CenterCriteria;
import com.isoft.tms.service.CenterQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CenterResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class CenterResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final Instant DEFAULT_START_WORKING_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_WORKING_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_WORKING_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_WORKING_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHONE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private CenterMapper centerMapper;

    @Autowired
    private CenterService centerService;

    @Autowired
    private CenterQueryService centerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCenterMockMvc;

    private Center center;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createEntity(EntityManager em) {
        Center center = new Center()
            .nameEn(DEFAULT_NAME_EN)
            .nameAr(DEFAULT_NAME_AR)
            .address(DEFAULT_ADDRESS)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .startWorkingHour(DEFAULT_START_WORKING_HOUR)
            .endWorkingHour(DEFAULT_END_WORKING_HOUR)
            .phoneNo(DEFAULT_PHONE_NO)
            .mobileNo(DEFAULT_MOBILE_NO)
            .email(DEFAULT_EMAIL)
            .country(DEFAULT_COUNTRY)
            .city(DEFAULT_CITY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .status(DEFAULT_STATUS);
        return center;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Center createUpdatedEntity(EntityManager em) {
        Center center = new Center()
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR)
            .address(UPDATED_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .startWorkingHour(UPDATED_START_WORKING_HOUR)
            .endWorkingHour(UPDATED_END_WORKING_HOUR)
            .phoneNo(UPDATED_PHONE_NO)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS);
        return center;
    }

    @BeforeEach
    public void initTest() {
        center = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenter() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);
        restCenterMockMvc.perform(post("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isCreated());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate + 1);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testCenter.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testCenter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCenter.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testCenter.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testCenter.getStartWorkingHour()).isEqualTo(DEFAULT_START_WORKING_HOUR);
        assertThat(testCenter.getEndWorkingHour()).isEqualTo(DEFAULT_END_WORKING_HOUR);
        assertThat(testCenter.getPhoneNo()).isEqualTo(DEFAULT_PHONE_NO);
        assertThat(testCenter.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testCenter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCenter.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCenter.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCenter.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCenter.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCenterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centerRepository.findAll().size();

        // Create the Center with an existing ID
        center.setId(1L);
        CenterDTO centerDTO = centerMapper.toDto(center);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterMockMvc.perform(post("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerRepository.findAll().size();
        // set the field null
        center.setNameEn(null);

        // Create the Center, which fails.
        CenterDTO centerDTO = centerMapper.toDto(center);

        restCenterMockMvc.perform(post("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = centerRepository.findAll().size();
        // set the field null
        center.setNameAr(null);

        // Create the Center, which fails.
        CenterDTO centerDTO = centerMapper.toDto(center);

        restCenterMockMvc.perform(post("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCenters() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(center.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].startWorkingHour").value(hasItem(DEFAULT_START_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endWorkingHour").value(hasItem(DEFAULT_END_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", center.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(center.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.startWorkingHour").value(DEFAULT_START_WORKING_HOUR.toString()))
            .andExpect(jsonPath("$.endWorkingHour").value(DEFAULT_END_WORKING_HOUR.toString()))
            .andExpect(jsonPath("$.phoneNo").value(DEFAULT_PHONE_NO))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getCentersByIdFiltering() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        Long id = center.getId();

        defaultCenterShouldBeFound("id.equals=" + id);
        defaultCenterShouldNotBeFound("id.notEquals=" + id);

        defaultCenterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCenterShouldNotBeFound("id.greaterThan=" + id);

        defaultCenterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCenterShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCentersByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn equals to DEFAULT_NAME_EN
        defaultCenterShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn equals to UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn not equals to DEFAULT_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn not equals to UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the centerList where nameEn equals to UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn is not null
        defaultCenterShouldBeFound("nameEn.specified=true");

        // Get all the centerList where nameEn is null
        defaultCenterShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByNameEnContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn contains DEFAULT_NAME_EN
        defaultCenterShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn contains UPDATED_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllCentersByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameEn does not contain DEFAULT_NAME_EN
        defaultCenterShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the centerList where nameEn does not contain UPDATED_NAME_EN
        defaultCenterShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllCentersByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr equals to DEFAULT_NAME_AR
        defaultCenterShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr equals to UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr not equals to DEFAULT_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr not equals to UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the centerList where nameAr equals to UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr is not null
        defaultCenterShouldBeFound("nameAr.specified=true");

        // Get all the centerList where nameAr is null
        defaultCenterShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByNameArContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr contains DEFAULT_NAME_AR
        defaultCenterShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr contains UPDATED_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllCentersByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where nameAr does not contain DEFAULT_NAME_AR
        defaultCenterShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the centerList where nameAr does not contain UPDATED_NAME_AR
        defaultCenterShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllCentersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address equals to DEFAULT_ADDRESS
        defaultCenterShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the centerList where address equals to UPDATED_ADDRESS
        defaultCenterShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCentersByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address not equals to DEFAULT_ADDRESS
        defaultCenterShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the centerList where address not equals to UPDATED_ADDRESS
        defaultCenterShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCentersByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCenterShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the centerList where address equals to UPDATED_ADDRESS
        defaultCenterShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCentersByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address is not null
        defaultCenterShouldBeFound("address.specified=true");

        // Get all the centerList where address is null
        defaultCenterShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByAddressContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address contains DEFAULT_ADDRESS
        defaultCenterShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the centerList where address contains UPDATED_ADDRESS
        defaultCenterShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCentersByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where address does not contain DEFAULT_ADDRESS
        defaultCenterShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the centerList where address does not contain UPDATED_ADDRESS
        defaultCenterShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllCentersByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude equals to DEFAULT_LONGITUDE
        defaultCenterShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude equals to UPDATED_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude not equals to DEFAULT_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude not equals to UPDATED_LONGITUDE
        defaultCenterShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultCenterShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the centerList where longitude equals to UPDATED_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude is not null
        defaultCenterShouldBeFound("longitude.specified=true");

        // Get all the centerList where longitude is null
        defaultCenterShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultCenterShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultCenterShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude is less than DEFAULT_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude is less than UPDATED_LONGITUDE
        defaultCenterShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where longitude is greater than DEFAULT_LONGITUDE
        defaultCenterShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the centerList where longitude is greater than SMALLER_LONGITUDE
        defaultCenterShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }


    @Test
    @Transactional
    public void getAllCentersByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude equals to DEFAULT_LATITUDE
        defaultCenterShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude equals to UPDATED_LATITUDE
        defaultCenterShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude not equals to DEFAULT_LATITUDE
        defaultCenterShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude not equals to UPDATED_LATITUDE
        defaultCenterShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultCenterShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the centerList where latitude equals to UPDATED_LATITUDE
        defaultCenterShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude is not null
        defaultCenterShouldBeFound("latitude.specified=true");

        // Get all the centerList where latitude is null
        defaultCenterShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultCenterShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultCenterShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultCenterShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude is less than or equal to SMALLER_LATITUDE
        defaultCenterShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude is less than DEFAULT_LATITUDE
        defaultCenterShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude is less than UPDATED_LATITUDE
        defaultCenterShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllCentersByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where latitude is greater than DEFAULT_LATITUDE
        defaultCenterShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the centerList where latitude is greater than SMALLER_LATITUDE
        defaultCenterShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllCentersByStartWorkingHourIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where startWorkingHour equals to DEFAULT_START_WORKING_HOUR
        defaultCenterShouldBeFound("startWorkingHour.equals=" + DEFAULT_START_WORKING_HOUR);

        // Get all the centerList where startWorkingHour equals to UPDATED_START_WORKING_HOUR
        defaultCenterShouldNotBeFound("startWorkingHour.equals=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByStartWorkingHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where startWorkingHour not equals to DEFAULT_START_WORKING_HOUR
        defaultCenterShouldNotBeFound("startWorkingHour.notEquals=" + DEFAULT_START_WORKING_HOUR);

        // Get all the centerList where startWorkingHour not equals to UPDATED_START_WORKING_HOUR
        defaultCenterShouldBeFound("startWorkingHour.notEquals=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByStartWorkingHourIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where startWorkingHour in DEFAULT_START_WORKING_HOUR or UPDATED_START_WORKING_HOUR
        defaultCenterShouldBeFound("startWorkingHour.in=" + DEFAULT_START_WORKING_HOUR + "," + UPDATED_START_WORKING_HOUR);

        // Get all the centerList where startWorkingHour equals to UPDATED_START_WORKING_HOUR
        defaultCenterShouldNotBeFound("startWorkingHour.in=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByStartWorkingHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where startWorkingHour is not null
        defaultCenterShouldBeFound("startWorkingHour.specified=true");

        // Get all the centerList where startWorkingHour is null
        defaultCenterShouldNotBeFound("startWorkingHour.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByEndWorkingHourIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where endWorkingHour equals to DEFAULT_END_WORKING_HOUR
        defaultCenterShouldBeFound("endWorkingHour.equals=" + DEFAULT_END_WORKING_HOUR);

        // Get all the centerList where endWorkingHour equals to UPDATED_END_WORKING_HOUR
        defaultCenterShouldNotBeFound("endWorkingHour.equals=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByEndWorkingHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where endWorkingHour not equals to DEFAULT_END_WORKING_HOUR
        defaultCenterShouldNotBeFound("endWorkingHour.notEquals=" + DEFAULT_END_WORKING_HOUR);

        // Get all the centerList where endWorkingHour not equals to UPDATED_END_WORKING_HOUR
        defaultCenterShouldBeFound("endWorkingHour.notEquals=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByEndWorkingHourIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where endWorkingHour in DEFAULT_END_WORKING_HOUR or UPDATED_END_WORKING_HOUR
        defaultCenterShouldBeFound("endWorkingHour.in=" + DEFAULT_END_WORKING_HOUR + "," + UPDATED_END_WORKING_HOUR);

        // Get all the centerList where endWorkingHour equals to UPDATED_END_WORKING_HOUR
        defaultCenterShouldNotBeFound("endWorkingHour.in=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCentersByEndWorkingHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where endWorkingHour is not null
        defaultCenterShouldBeFound("endWorkingHour.specified=true");

        // Get all the centerList where endWorkingHour is null
        defaultCenterShouldNotBeFound("endWorkingHour.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByPhoneNoIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo equals to DEFAULT_PHONE_NO
        defaultCenterShouldBeFound("phoneNo.equals=" + DEFAULT_PHONE_NO);

        // Get all the centerList where phoneNo equals to UPDATED_PHONE_NO
        defaultCenterShouldNotBeFound("phoneNo.equals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByPhoneNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo not equals to DEFAULT_PHONE_NO
        defaultCenterShouldNotBeFound("phoneNo.notEquals=" + DEFAULT_PHONE_NO);

        // Get all the centerList where phoneNo not equals to UPDATED_PHONE_NO
        defaultCenterShouldBeFound("phoneNo.notEquals=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByPhoneNoIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo in DEFAULT_PHONE_NO or UPDATED_PHONE_NO
        defaultCenterShouldBeFound("phoneNo.in=" + DEFAULT_PHONE_NO + "," + UPDATED_PHONE_NO);

        // Get all the centerList where phoneNo equals to UPDATED_PHONE_NO
        defaultCenterShouldNotBeFound("phoneNo.in=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByPhoneNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo is not null
        defaultCenterShouldBeFound("phoneNo.specified=true");

        // Get all the centerList where phoneNo is null
        defaultCenterShouldNotBeFound("phoneNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByPhoneNoContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo contains DEFAULT_PHONE_NO
        defaultCenterShouldBeFound("phoneNo.contains=" + DEFAULT_PHONE_NO);

        // Get all the centerList where phoneNo contains UPDATED_PHONE_NO
        defaultCenterShouldNotBeFound("phoneNo.contains=" + UPDATED_PHONE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByPhoneNoNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where phoneNo does not contain DEFAULT_PHONE_NO
        defaultCenterShouldNotBeFound("phoneNo.doesNotContain=" + DEFAULT_PHONE_NO);

        // Get all the centerList where phoneNo does not contain UPDATED_PHONE_NO
        defaultCenterShouldBeFound("phoneNo.doesNotContain=" + UPDATED_PHONE_NO);
    }


    @Test
    @Transactional
    public void getAllCentersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultCenterShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the centerList where mobileNo equals to UPDATED_MOBILE_NO
        defaultCenterShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultCenterShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the centerList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultCenterShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultCenterShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the centerList where mobileNo equals to UPDATED_MOBILE_NO
        defaultCenterShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo is not null
        defaultCenterShouldBeFound("mobileNo.specified=true");

        // Get all the centerList where mobileNo is null
        defaultCenterShouldNotBeFound("mobileNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo contains DEFAULT_MOBILE_NO
        defaultCenterShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the centerList where mobileNo contains UPDATED_MOBILE_NO
        defaultCenterShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllCentersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultCenterShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the centerList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultCenterShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }


    @Test
    @Transactional
    public void getAllCentersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email equals to DEFAULT_EMAIL
        defaultCenterShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the centerList where email equals to UPDATED_EMAIL
        defaultCenterShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCentersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email not equals to DEFAULT_EMAIL
        defaultCenterShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the centerList where email not equals to UPDATED_EMAIL
        defaultCenterShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCentersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCenterShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the centerList where email equals to UPDATED_EMAIL
        defaultCenterShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCentersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email is not null
        defaultCenterShouldBeFound("email.specified=true");

        // Get all the centerList where email is null
        defaultCenterShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByEmailContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email contains DEFAULT_EMAIL
        defaultCenterShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the centerList where email contains UPDATED_EMAIL
        defaultCenterShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCentersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where email does not contain DEFAULT_EMAIL
        defaultCenterShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the centerList where email does not contain UPDATED_EMAIL
        defaultCenterShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCentersByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country equals to DEFAULT_COUNTRY
        defaultCenterShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the centerList where country equals to UPDATED_COUNTRY
        defaultCenterShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllCentersByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country not equals to DEFAULT_COUNTRY
        defaultCenterShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the centerList where country not equals to UPDATED_COUNTRY
        defaultCenterShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllCentersByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultCenterShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the centerList where country equals to UPDATED_COUNTRY
        defaultCenterShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllCentersByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country is not null
        defaultCenterShouldBeFound("country.specified=true");

        // Get all the centerList where country is null
        defaultCenterShouldNotBeFound("country.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByCountryContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country contains DEFAULT_COUNTRY
        defaultCenterShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the centerList where country contains UPDATED_COUNTRY
        defaultCenterShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllCentersByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where country does not contain DEFAULT_COUNTRY
        defaultCenterShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the centerList where country does not contain UPDATED_COUNTRY
        defaultCenterShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }


    @Test
    @Transactional
    public void getAllCentersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city equals to DEFAULT_CITY
        defaultCenterShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the centerList where city equals to UPDATED_CITY
        defaultCenterShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllCentersByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city not equals to DEFAULT_CITY
        defaultCenterShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the centerList where city not equals to UPDATED_CITY
        defaultCenterShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllCentersByCityIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city in DEFAULT_CITY or UPDATED_CITY
        defaultCenterShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the centerList where city equals to UPDATED_CITY
        defaultCenterShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllCentersByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city is not null
        defaultCenterShouldBeFound("city.specified=true");

        // Get all the centerList where city is null
        defaultCenterShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByCityContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city contains DEFAULT_CITY
        defaultCenterShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the centerList where city contains UPDATED_CITY
        defaultCenterShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllCentersByCityNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where city does not contain DEFAULT_CITY
        defaultCenterShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the centerList where city does not contain UPDATED_CITY
        defaultCenterShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllCentersByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultCenterShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the centerList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCenterShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCentersByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultCenterShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the centerList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultCenterShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCentersByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultCenterShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the centerList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCenterShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCentersByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl is not null
        defaultCenterShouldBeFound("imageUrl.specified=true");

        // Get all the centerList where imageUrl is null
        defaultCenterShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCentersByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl contains DEFAULT_IMAGE_URL
        defaultCenterShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the centerList where imageUrl contains UPDATED_IMAGE_URL
        defaultCenterShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCentersByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultCenterShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the centerList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultCenterShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllCentersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status equals to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the centerList where status equals to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status not equals to DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the centerList where status not equals to UPDATED_STATUS
        defaultCenterShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCenterShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the centerList where status equals to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is not null
        defaultCenterShouldBeFound("status.specified=true");

        // Get all the centerList where status is null
        defaultCenterShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is greater than or equal to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the centerList where status is greater than or equal to UPDATED_STATUS
        defaultCenterShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is less than or equal to DEFAULT_STATUS
        defaultCenterShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the centerList where status is less than or equal to SMALLER_STATUS
        defaultCenterShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is less than DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the centerList where status is less than UPDATED_STATUS
        defaultCenterShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCentersByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        // Get all the centerList where status is greater than DEFAULT_STATUS
        defaultCenterShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the centerList where status is greater than SMALLER_STATUS
        defaultCenterShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllCentersByCenterWorkingDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);
        CenterWorkingDays centerWorkingDays = CenterWorkingDaysResourceIT.createEntity(em);
        em.persist(centerWorkingDays);
        em.flush();
        center.setCenterWorkingDays(centerWorkingDays);
        centerRepository.saveAndFlush(center);
        Long centerWorkingDaysId = centerWorkingDays.getId();

        // Get all the centerList where centerWorkingDays equals to centerWorkingDaysId
        defaultCenterShouldBeFound("centerWorkingDaysId.equals=" + centerWorkingDaysId);

        // Get all the centerList where centerWorkingDays equals to centerWorkingDaysId + 1
        defaultCenterShouldNotBeFound("centerWorkingDaysId.equals=" + (centerWorkingDaysId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCenterShouldBeFound(String filter) throws Exception {
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(center.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].startWorkingHour").value(hasItem(DEFAULT_START_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endWorkingHour").value(hasItem(DEFAULT_END_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].phoneNo").value(hasItem(DEFAULT_PHONE_NO)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restCenterMockMvc.perform(get("/api/centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCenterShouldNotBeFound(String filter) throws Exception {
        restCenterMockMvc.perform(get("/api/centers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCenterMockMvc.perform(get("/api/centers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCenter() throws Exception {
        // Get the center
        restCenterMockMvc.perform(get("/api/centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Update the center
        Center updatedCenter = centerRepository.findById(center.getId()).get();
        // Disconnect from session so that the updates on updatedCenter are not directly saved in db
        em.detach(updatedCenter);
        updatedCenter
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR)
            .address(UPDATED_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .startWorkingHour(UPDATED_START_WORKING_HOUR)
            .endWorkingHour(UPDATED_END_WORKING_HOUR)
            .phoneNo(UPDATED_PHONE_NO)
            .mobileNo(UPDATED_MOBILE_NO)
            .email(UPDATED_EMAIL)
            .country(UPDATED_COUNTRY)
            .city(UPDATED_CITY)
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS);
        CenterDTO centerDTO = centerMapper.toDto(updatedCenter);

        restCenterMockMvc.perform(put("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isOk());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
        Center testCenter = centerList.get(centerList.size() - 1);
        assertThat(testCenter.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testCenter.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testCenter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCenter.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCenter.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCenter.getStartWorkingHour()).isEqualTo(UPDATED_START_WORKING_HOUR);
        assertThat(testCenter.getEndWorkingHour()).isEqualTo(UPDATED_END_WORKING_HOUR);
        assertThat(testCenter.getPhoneNo()).isEqualTo(UPDATED_PHONE_NO);
        assertThat(testCenter.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testCenter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCenter.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCenter.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCenter.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCenter.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCenter() throws Exception {
        int databaseSizeBeforeUpdate = centerRepository.findAll().size();

        // Create the Center
        CenterDTO centerDTO = centerMapper.toDto(center);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterMockMvc.perform(put("/api/centers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Center in the database
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenter() throws Exception {
        // Initialize the database
        centerRepository.saveAndFlush(center);

        int databaseSizeBeforeDelete = centerRepository.findAll().size();

        // Delete the center
        restCenterMockMvc.perform(delete("/api/centers/{id}", center.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Center> centerList = centerRepository.findAll();
        assertThat(centerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
