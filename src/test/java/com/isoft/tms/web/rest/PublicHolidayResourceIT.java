package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.PublicHoliday;
import com.isoft.tms.repository.PublicHolidayRepository;
import com.isoft.tms.service.PublicHolidayService;
import com.isoft.tms.service.dto.PublicHolidayDTO;
import com.isoft.tms.service.mapper.PublicHolidayMapper;
import com.isoft.tms.service.dto.PublicHolidayCriteria;
import com.isoft.tms.service.PublicHolidayQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PublicHolidayResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class PublicHolidayResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

    @Autowired
    private PublicHolidayMapper publicHolidayMapper;

    @Autowired
    private PublicHolidayService publicHolidayService;

    @Autowired
    private PublicHolidayQueryService publicHolidayQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPublicHolidayMockMvc;

    private PublicHoliday publicHoliday;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHoliday createEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .description(DEFAULT_DESCRIPTION)
            .year(DEFAULT_YEAR)
            .type(DEFAULT_TYPE)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO);
        return publicHoliday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHoliday createUpdatedEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .description(UPDATED_DESCRIPTION)
            .year(UPDATED_YEAR)
            .type(UPDATED_TYPE)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO);
        return publicHoliday;
    }

    @BeforeEach
    public void initTest() {
        publicHoliday = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicHoliday() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);
        restPublicHolidayMockMvc.perform(post("/api/public-holidays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isCreated());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate + 1);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPublicHoliday.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testPublicHoliday.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPublicHoliday.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testPublicHoliday.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
    }

    @Test
    @Transactional
    public void createPublicHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday with an existing ID
        publicHoliday.setId(1L);
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicHolidayMockMvc.perform(post("/api/public-holidays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicHolidayRepository.findAll().size();
        // set the field null
        publicHoliday.setDescription(null);

        // Create the PublicHoliday, which fails.
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);

        restPublicHolidayMockMvc.perform(post("/api/public-holidays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isBadRequest());

        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicHolidays() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", publicHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicHoliday.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()));
    }


    @Test
    @Transactional
    public void getPublicHolidaysByIdFiltering() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        Long id = publicHoliday.getId();

        defaultPublicHolidayShouldBeFound("id.equals=" + id);
        defaultPublicHolidayShouldNotBeFound("id.notEquals=" + id);

        defaultPublicHolidayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPublicHolidayShouldNotBeFound("id.greaterThan=" + id);

        defaultPublicHolidayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPublicHolidayShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description equals to DEFAULT_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the publicHolidayList where description equals to UPDATED_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description not equals to DEFAULT_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the publicHolidayList where description not equals to UPDATED_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the publicHolidayList where description equals to UPDATED_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description is not null
        defaultPublicHolidayShouldBeFound("description.specified=true");

        // Get all the publicHolidayList where description is null
        defaultPublicHolidayShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description contains DEFAULT_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the publicHolidayList where description contains UPDATED_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description does not contain DEFAULT_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the publicHolidayList where description does not contain UPDATED_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year equals to DEFAULT_YEAR
        defaultPublicHolidayShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year equals to UPDATED_YEAR
        defaultPublicHolidayShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year not equals to DEFAULT_YEAR
        defaultPublicHolidayShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year not equals to UPDATED_YEAR
        defaultPublicHolidayShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultPublicHolidayShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the publicHolidayList where year equals to UPDATED_YEAR
        defaultPublicHolidayShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year is not null
        defaultPublicHolidayShouldBeFound("year.specified=true");

        // Get all the publicHolidayList where year is null
        defaultPublicHolidayShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year is greater than or equal to DEFAULT_YEAR
        defaultPublicHolidayShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year is greater than or equal to UPDATED_YEAR
        defaultPublicHolidayShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year is less than or equal to DEFAULT_YEAR
        defaultPublicHolidayShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year is less than or equal to SMALLER_YEAR
        defaultPublicHolidayShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year is less than DEFAULT_YEAR
        defaultPublicHolidayShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year is less than UPDATED_YEAR
        defaultPublicHolidayShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where year is greater than DEFAULT_YEAR
        defaultPublicHolidayShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the publicHolidayList where year is greater than SMALLER_YEAR
        defaultPublicHolidayShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type equals to DEFAULT_TYPE
        defaultPublicHolidayShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type equals to UPDATED_TYPE
        defaultPublicHolidayShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type not equals to DEFAULT_TYPE
        defaultPublicHolidayShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type not equals to UPDATED_TYPE
        defaultPublicHolidayShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPublicHolidayShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the publicHolidayList where type equals to UPDATED_TYPE
        defaultPublicHolidayShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type is not null
        defaultPublicHolidayShouldBeFound("type.specified=true");

        // Get all the publicHolidayList where type is null
        defaultPublicHolidayShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type is greater than or equal to DEFAULT_TYPE
        defaultPublicHolidayShouldBeFound("type.greaterThanOrEqual=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type is greater than or equal to UPDATED_TYPE
        defaultPublicHolidayShouldNotBeFound("type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type is less than or equal to DEFAULT_TYPE
        defaultPublicHolidayShouldBeFound("type.lessThanOrEqual=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type is less than or equal to SMALLER_TYPE
        defaultPublicHolidayShouldNotBeFound("type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type is less than DEFAULT_TYPE
        defaultPublicHolidayShouldNotBeFound("type.lessThan=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type is less than UPDATED_TYPE
        defaultPublicHolidayShouldBeFound("type.lessThan=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where type is greater than DEFAULT_TYPE
        defaultPublicHolidayShouldNotBeFound("type.greaterThan=" + DEFAULT_TYPE);

        // Get all the publicHolidayList where type is greater than SMALLER_TYPE
        defaultPublicHolidayShouldBeFound("type.greaterThan=" + SMALLER_TYPE);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom equals to DEFAULT_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.equals=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom equals to UPDATED_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.equals=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom not equals to DEFAULT_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.notEquals=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom not equals to UPDATED_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.notEquals=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom in DEFAULT_DATE_FROM or UPDATED_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.in=" + DEFAULT_DATE_FROM + "," + UPDATED_DATE_FROM);

        // Get all the publicHolidayList where dateFrom equals to UPDATED_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.in=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom is not null
        defaultPublicHolidayShouldBeFound("dateFrom.specified=true");

        // Get all the publicHolidayList where dateFrom is null
        defaultPublicHolidayShouldNotBeFound("dateFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom is greater than or equal to DEFAULT_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.greaterThanOrEqual=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom is greater than or equal to UPDATED_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.greaterThanOrEqual=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom is less than or equal to DEFAULT_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.lessThanOrEqual=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom is less than or equal to SMALLER_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.lessThanOrEqual=" + SMALLER_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom is less than DEFAULT_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.lessThan=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom is less than UPDATED_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.lessThan=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateFrom is greater than DEFAULT_DATE_FROM
        defaultPublicHolidayShouldNotBeFound("dateFrom.greaterThan=" + DEFAULT_DATE_FROM);

        // Get all the publicHolidayList where dateFrom is greater than SMALLER_DATE_FROM
        defaultPublicHolidayShouldBeFound("dateFrom.greaterThan=" + SMALLER_DATE_FROM);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo equals to DEFAULT_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.equals=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo equals to UPDATED_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.equals=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo not equals to DEFAULT_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.notEquals=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo not equals to UPDATED_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.notEquals=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo in DEFAULT_DATE_TO or UPDATED_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.in=" + DEFAULT_DATE_TO + "," + UPDATED_DATE_TO);

        // Get all the publicHolidayList where dateTo equals to UPDATED_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.in=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo is not null
        defaultPublicHolidayShouldBeFound("dateTo.specified=true");

        // Get all the publicHolidayList where dateTo is null
        defaultPublicHolidayShouldNotBeFound("dateTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo is greater than or equal to DEFAULT_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.greaterThanOrEqual=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo is greater than or equal to UPDATED_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.greaterThanOrEqual=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo is less than or equal to DEFAULT_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.lessThanOrEqual=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo is less than or equal to SMALLER_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.lessThanOrEqual=" + SMALLER_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo is less than DEFAULT_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.lessThan=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo is less than UPDATED_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.lessThan=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where dateTo is greater than DEFAULT_DATE_TO
        defaultPublicHolidayShouldNotBeFound("dateTo.greaterThan=" + DEFAULT_DATE_TO);

        // Get all the publicHolidayList where dateTo is greater than SMALLER_DATE_TO
        defaultPublicHolidayShouldBeFound("dateTo.greaterThan=" + SMALLER_DATE_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublicHolidayShouldBeFound(String filter) throws Exception {
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())));

        // Check, that the count call also returns 1
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublicHolidayShouldNotBeFound(String filter) throws Exception {
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPublicHoliday() throws Exception {
        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Update the publicHoliday
        PublicHoliday updatedPublicHoliday = publicHolidayRepository.findById(publicHoliday.getId()).get();
        // Disconnect from session so that the updates on updatedPublicHoliday are not directly saved in db
        em.detach(updatedPublicHoliday);
        updatedPublicHoliday
            .description(UPDATED_DESCRIPTION)
            .year(UPDATED_YEAR)
            .type(UPDATED_TYPE)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO);
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(updatedPublicHoliday);

        restPublicHolidayMockMvc.perform(put("/api/public-holidays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isOk());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPublicHoliday.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testPublicHoliday.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPublicHoliday.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testPublicHoliday.getDateTo()).isEqualTo(UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicHoliday() throws Exception {
        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday
        PublicHolidayDTO publicHolidayDTO = publicHolidayMapper.toDto(publicHoliday);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicHolidayMockMvc.perform(put("/api/public-holidays").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicHolidayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeDelete = publicHolidayRepository.findAll().size();

        // Delete the publicHoliday
        restPublicHolidayMockMvc.perform(delete("/api/public-holidays/{id}", publicHoliday.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
