package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.EmployeeVacation;
import com.isoft.tms.domain.Employee;
import com.isoft.tms.repository.EmployeeVacationRepository;
import com.isoft.tms.service.EmployeeVacationService;
import com.isoft.tms.service.dto.EmployeeVacationDTO;
import com.isoft.tms.service.mapper.EmployeeVacationMapper;
import com.isoft.tms.service.dto.EmployeeVacationCriteria;
import com.isoft.tms.service.EmployeeVacationQueryService;

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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeVacationResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class EmployeeVacationResourceIT {

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TO = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_TIME_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;
    private static final Integer SMALLER_TYPE = 1 - 1;

    @Autowired
    private EmployeeVacationRepository employeeVacationRepository;

    @Autowired
    private EmployeeVacationMapper employeeVacationMapper;

    @Autowired
    private EmployeeVacationService employeeVacationService;

    @Autowired
    private EmployeeVacationQueryService employeeVacationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeVacationMockMvc;

    private EmployeeVacation employeeVacation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeVacation createEntity(EntityManager em) {
        EmployeeVacation employeeVacation = new EmployeeVacation()
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE);
        return employeeVacation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeVacation createUpdatedEntity(EntityManager em) {
        EmployeeVacation employeeVacation = new EmployeeVacation()
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);
        return employeeVacation;
    }

    @BeforeEach
    public void initTest() {
        employeeVacation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeVacation() throws Exception {
        int databaseSizeBeforeCreate = employeeVacationRepository.findAll().size();

        // Create the EmployeeVacation
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(employeeVacation);
        restEmployeeVacationMockMvc.perform(post("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeVacation in the database
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeVacation testEmployeeVacation = employeeVacationList.get(employeeVacationList.size() - 1);
        assertThat(testEmployeeVacation.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testEmployeeVacation.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testEmployeeVacation.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testEmployeeVacation.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
        assertThat(testEmployeeVacation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployeeVacation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createEmployeeVacationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeVacationRepository.findAll().size();

        // Create the EmployeeVacation with an existing ID
        employeeVacation.setId(1L);
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(employeeVacation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeVacationMockMvc.perform(post("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeVacation in the database
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeVacationRepository.findAll().size();
        // set the field null
        employeeVacation.setDateFrom(null);

        // Create the EmployeeVacation, which fails.
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(employeeVacation);

        restEmployeeVacationMockMvc.perform(post("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateToIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeVacationRepository.findAll().size();
        // set the field null
        employeeVacation.setDateTo(null);

        // Create the EmployeeVacation, which fails.
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(employeeVacation);

        restEmployeeVacationMockMvc.perform(post("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacations() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeVacation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getEmployeeVacation() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get the employeeVacation
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations/{id}", employeeVacation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeVacation.getId().intValue()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.timeFrom").value(DEFAULT_TIME_FROM.toString()))
            .andExpect(jsonPath("$.timeTo").value(DEFAULT_TIME_TO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getEmployeeVacationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        Long id = employeeVacation.getId();

        defaultEmployeeVacationShouldBeFound("id.equals=" + id);
        defaultEmployeeVacationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeVacationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeVacationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeVacationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeVacationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom equals to DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.equals=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom equals to UPDATED_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.equals=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom not equals to DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.notEquals=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom not equals to UPDATED_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.notEquals=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom in DEFAULT_DATE_FROM or UPDATED_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.in=" + DEFAULT_DATE_FROM + "," + UPDATED_DATE_FROM);

        // Get all the employeeVacationList where dateFrom equals to UPDATED_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.in=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom is not null
        defaultEmployeeVacationShouldBeFound("dateFrom.specified=true");

        // Get all the employeeVacationList where dateFrom is null
        defaultEmployeeVacationShouldNotBeFound("dateFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom is greater than or equal to DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.greaterThanOrEqual=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom is greater than or equal to UPDATED_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.greaterThanOrEqual=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom is less than or equal to DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.lessThanOrEqual=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom is less than or equal to SMALLER_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.lessThanOrEqual=" + SMALLER_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom is less than DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.lessThan=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom is less than UPDATED_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.lessThan=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateFrom is greater than DEFAULT_DATE_FROM
        defaultEmployeeVacationShouldNotBeFound("dateFrom.greaterThan=" + DEFAULT_DATE_FROM);

        // Get all the employeeVacationList where dateFrom is greater than SMALLER_DATE_FROM
        defaultEmployeeVacationShouldBeFound("dateFrom.greaterThan=" + SMALLER_DATE_FROM);
    }


    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo equals to DEFAULT_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.equals=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo equals to UPDATED_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.equals=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo not equals to DEFAULT_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.notEquals=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo not equals to UPDATED_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.notEquals=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo in DEFAULT_DATE_TO or UPDATED_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.in=" + DEFAULT_DATE_TO + "," + UPDATED_DATE_TO);

        // Get all the employeeVacationList where dateTo equals to UPDATED_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.in=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo is not null
        defaultEmployeeVacationShouldBeFound("dateTo.specified=true");

        // Get all the employeeVacationList where dateTo is null
        defaultEmployeeVacationShouldNotBeFound("dateTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo is greater than or equal to DEFAULT_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.greaterThanOrEqual=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo is greater than or equal to UPDATED_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.greaterThanOrEqual=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo is less than or equal to DEFAULT_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.lessThanOrEqual=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo is less than or equal to SMALLER_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.lessThanOrEqual=" + SMALLER_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo is less than DEFAULT_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.lessThan=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo is less than UPDATED_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.lessThan=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByDateToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where dateTo is greater than DEFAULT_DATE_TO
        defaultEmployeeVacationShouldNotBeFound("dateTo.greaterThan=" + DEFAULT_DATE_TO);

        // Get all the employeeVacationList where dateTo is greater than SMALLER_DATE_TO
        defaultEmployeeVacationShouldBeFound("dateTo.greaterThan=" + SMALLER_DATE_TO);
    }


    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeFromIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeFrom equals to DEFAULT_TIME_FROM
        defaultEmployeeVacationShouldBeFound("timeFrom.equals=" + DEFAULT_TIME_FROM);

        // Get all the employeeVacationList where timeFrom equals to UPDATED_TIME_FROM
        defaultEmployeeVacationShouldNotBeFound("timeFrom.equals=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeFrom not equals to DEFAULT_TIME_FROM
        defaultEmployeeVacationShouldNotBeFound("timeFrom.notEquals=" + DEFAULT_TIME_FROM);

        // Get all the employeeVacationList where timeFrom not equals to UPDATED_TIME_FROM
        defaultEmployeeVacationShouldBeFound("timeFrom.notEquals=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeFromIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeFrom in DEFAULT_TIME_FROM or UPDATED_TIME_FROM
        defaultEmployeeVacationShouldBeFound("timeFrom.in=" + DEFAULT_TIME_FROM + "," + UPDATED_TIME_FROM);

        // Get all the employeeVacationList where timeFrom equals to UPDATED_TIME_FROM
        defaultEmployeeVacationShouldNotBeFound("timeFrom.in=" + UPDATED_TIME_FROM);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeFrom is not null
        defaultEmployeeVacationShouldBeFound("timeFrom.specified=true");

        // Get all the employeeVacationList where timeFrom is null
        defaultEmployeeVacationShouldNotBeFound("timeFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeToIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeTo equals to DEFAULT_TIME_TO
        defaultEmployeeVacationShouldBeFound("timeTo.equals=" + DEFAULT_TIME_TO);

        // Get all the employeeVacationList where timeTo equals to UPDATED_TIME_TO
        defaultEmployeeVacationShouldNotBeFound("timeTo.equals=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeTo not equals to DEFAULT_TIME_TO
        defaultEmployeeVacationShouldNotBeFound("timeTo.notEquals=" + DEFAULT_TIME_TO);

        // Get all the employeeVacationList where timeTo not equals to UPDATED_TIME_TO
        defaultEmployeeVacationShouldBeFound("timeTo.notEquals=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeToIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeTo in DEFAULT_TIME_TO or UPDATED_TIME_TO
        defaultEmployeeVacationShouldBeFound("timeTo.in=" + DEFAULT_TIME_TO + "," + UPDATED_TIME_TO);

        // Get all the employeeVacationList where timeTo equals to UPDATED_TIME_TO
        defaultEmployeeVacationShouldNotBeFound("timeTo.in=" + UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTimeToIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where timeTo is not null
        defaultEmployeeVacationShouldBeFound("timeTo.specified=true");

        // Get all the employeeVacationList where timeTo is null
        defaultEmployeeVacationShouldNotBeFound("timeTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status equals to DEFAULT_STATUS
        defaultEmployeeVacationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status equals to UPDATED_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status not equals to DEFAULT_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status not equals to UPDATED_STATUS
        defaultEmployeeVacationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeVacationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeVacationList where status equals to UPDATED_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status is not null
        defaultEmployeeVacationShouldBeFound("status.specified=true");

        // Get all the employeeVacationList where status is null
        defaultEmployeeVacationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status is greater than or equal to DEFAULT_STATUS
        defaultEmployeeVacationShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status is greater than or equal to UPDATED_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status is less than or equal to DEFAULT_STATUS
        defaultEmployeeVacationShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status is less than or equal to SMALLER_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status is less than DEFAULT_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status is less than UPDATED_STATUS
        defaultEmployeeVacationShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where status is greater than DEFAULT_STATUS
        defaultEmployeeVacationShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the employeeVacationList where status is greater than SMALLER_STATUS
        defaultEmployeeVacationShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type equals to DEFAULT_TYPE
        defaultEmployeeVacationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type equals to UPDATED_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type not equals to DEFAULT_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type not equals to UPDATED_TYPE
        defaultEmployeeVacationShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployeeVacationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employeeVacationList where type equals to UPDATED_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type is not null
        defaultEmployeeVacationShouldBeFound("type.specified=true");

        // Get all the employeeVacationList where type is null
        defaultEmployeeVacationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type is greater than or equal to DEFAULT_TYPE
        defaultEmployeeVacationShouldBeFound("type.greaterThanOrEqual=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type is greater than or equal to UPDATED_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.greaterThanOrEqual=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type is less than or equal to DEFAULT_TYPE
        defaultEmployeeVacationShouldBeFound("type.lessThanOrEqual=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type is less than or equal to SMALLER_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.lessThanOrEqual=" + SMALLER_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type is less than DEFAULT_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.lessThan=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type is less than UPDATED_TYPE
        defaultEmployeeVacationShouldBeFound("type.lessThan=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmployeeVacationsByTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        // Get all the employeeVacationList where type is greater than DEFAULT_TYPE
        defaultEmployeeVacationShouldNotBeFound("type.greaterThan=" + DEFAULT_TYPE);

        // Get all the employeeVacationList where type is greater than SMALLER_TYPE
        defaultEmployeeVacationShouldBeFound("type.greaterThan=" + SMALLER_TYPE);
    }


    @Test
    @Transactional
    public void getAllEmployeeVacationsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        employeeVacation.setEmployee(employee);
        employeeVacationRepository.saveAndFlush(employeeVacation);
        Long employeeId = employee.getId();

        // Get all the employeeVacationList where employee equals to employeeId
        defaultEmployeeVacationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeVacationList where employee equals to employeeId + 1
        defaultEmployeeVacationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeVacationShouldBeFound(String filter) throws Exception {
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeVacation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(DEFAULT_TIME_FROM.toString())))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(DEFAULT_TIME_TO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeVacationShouldNotBeFound(String filter) throws Exception {
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployeeVacation() throws Exception {
        // Get the employeeVacation
        restEmployeeVacationMockMvc.perform(get("/api/employee-vacations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeVacation() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        int databaseSizeBeforeUpdate = employeeVacationRepository.findAll().size();

        // Update the employeeVacation
        EmployeeVacation updatedEmployeeVacation = employeeVacationRepository.findById(employeeVacation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeVacation are not directly saved in db
        em.detach(updatedEmployeeVacation);
        updatedEmployeeVacation
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE);
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(updatedEmployeeVacation);

        restEmployeeVacationMockMvc.perform(put("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeVacation in the database
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeVacation testEmployeeVacation = employeeVacationList.get(employeeVacationList.size() - 1);
        assertThat(testEmployeeVacation.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testEmployeeVacation.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testEmployeeVacation.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testEmployeeVacation.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
        assertThat(testEmployeeVacation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployeeVacation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeVacation() throws Exception {
        int databaseSizeBeforeUpdate = employeeVacationRepository.findAll().size();

        // Create the EmployeeVacation
        EmployeeVacationDTO employeeVacationDTO = employeeVacationMapper.toDto(employeeVacation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeVacationMockMvc.perform(put("/api/employee-vacations").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeVacationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeVacation in the database
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeVacation() throws Exception {
        // Initialize the database
        employeeVacationRepository.saveAndFlush(employeeVacation);

        int databaseSizeBeforeDelete = employeeVacationRepository.findAll().size();

        // Delete the employeeVacation
        restEmployeeVacationMockMvc.perform(delete("/api/employee-vacations/{id}", employeeVacation.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeVacation> employeeVacationList = employeeVacationRepository.findAll();
        assertThat(employeeVacationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
