package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.Employee;
import com.isoft.tms.domain.EmployeeVacation;
import com.isoft.tms.domain.Department;
import com.isoft.tms.domain.Center;
import com.isoft.tms.domain.EmployeeType;
import com.isoft.tms.repository.EmployeeRepository;
import com.isoft.tms.service.EmployeeService;
import com.isoft.tms.service.dto.EmployeeDTO;
import com.isoft.tms.service.mapper.EmployeeMapper;
import com.isoft.tms.service.dto.EmployeeCriteria;
import com.isoft.tms.service.EmployeeQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.isoft.tms.domain.enumeration.Language;
/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final String DEFAULT_FULL_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME_AR = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final Long SMALLER_USER_ID = 1L - 1L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Language DEFAULT_LANG_KEY = Language.ARABIC;
    private static final Language UPDATED_LANG_KEY = Language.ENGLISH;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeQueryService employeeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .fullNameEn(DEFAULT_FULL_NAME_EN)
            .fullNameAr(DEFAULT_FULL_NAME_AR)
            .userId(DEFAULT_USER_ID)
            .email(DEFAULT_EMAIL)
            .mobileNo(DEFAULT_MOBILE_NO)
            .status(DEFAULT_STATUS)
            .langKey(DEFAULT_LANG_KEY);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .status(UPDATED_STATUS)
            .langKey(UPDATED_LANG_KEY);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFullNameEn()).isEqualTo(DEFAULT_FULL_NAME_EN);
        assertThat(testEmployee.getFullNameAr()).isEqualTo(DEFAULT_FULL_NAME_AR);
        assertThat(testEmployee.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testEmployee.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployee.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFullNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFullNameEn(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFullNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setFullNameAr(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmail(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setMobileNo(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].fullNameAr").value(hasItem(DEFAULT_FULL_NAME_AR)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.fullNameEn").value(DEFAULT_FULL_NAME_EN))
            .andExpect(jsonPath("$.fullNameAr").value(DEFAULT_FULL_NAME_AR))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY.toString()));
    }


    @Test
    @Transactional
    public void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeesByFullNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn equals to DEFAULT_FULL_NAME_EN
        defaultEmployeeShouldBeFound("fullNameEn.equals=" + DEFAULT_FULL_NAME_EN);

        // Get all the employeeList where fullNameEn equals to UPDATED_FULL_NAME_EN
        defaultEmployeeShouldNotBeFound("fullNameEn.equals=" + UPDATED_FULL_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn not equals to DEFAULT_FULL_NAME_EN
        defaultEmployeeShouldNotBeFound("fullNameEn.notEquals=" + DEFAULT_FULL_NAME_EN);

        // Get all the employeeList where fullNameEn not equals to UPDATED_FULL_NAME_EN
        defaultEmployeeShouldBeFound("fullNameEn.notEquals=" + UPDATED_FULL_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn in DEFAULT_FULL_NAME_EN or UPDATED_FULL_NAME_EN
        defaultEmployeeShouldBeFound("fullNameEn.in=" + DEFAULT_FULL_NAME_EN + "," + UPDATED_FULL_NAME_EN);

        // Get all the employeeList where fullNameEn equals to UPDATED_FULL_NAME_EN
        defaultEmployeeShouldNotBeFound("fullNameEn.in=" + UPDATED_FULL_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn is not null
        defaultEmployeeShouldBeFound("fullNameEn.specified=true");

        // Get all the employeeList where fullNameEn is null
        defaultEmployeeShouldNotBeFound("fullNameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByFullNameEnContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn contains DEFAULT_FULL_NAME_EN
        defaultEmployeeShouldBeFound("fullNameEn.contains=" + DEFAULT_FULL_NAME_EN);

        // Get all the employeeList where fullNameEn contains UPDATED_FULL_NAME_EN
        defaultEmployeeShouldNotBeFound("fullNameEn.contains=" + UPDATED_FULL_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameEn does not contain DEFAULT_FULL_NAME_EN
        defaultEmployeeShouldNotBeFound("fullNameEn.doesNotContain=" + DEFAULT_FULL_NAME_EN);

        // Get all the employeeList where fullNameEn does not contain UPDATED_FULL_NAME_EN
        defaultEmployeeShouldBeFound("fullNameEn.doesNotContain=" + UPDATED_FULL_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllEmployeesByFullNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr equals to DEFAULT_FULL_NAME_AR
        defaultEmployeeShouldBeFound("fullNameAr.equals=" + DEFAULT_FULL_NAME_AR);

        // Get all the employeeList where fullNameAr equals to UPDATED_FULL_NAME_AR
        defaultEmployeeShouldNotBeFound("fullNameAr.equals=" + UPDATED_FULL_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr not equals to DEFAULT_FULL_NAME_AR
        defaultEmployeeShouldNotBeFound("fullNameAr.notEquals=" + DEFAULT_FULL_NAME_AR);

        // Get all the employeeList where fullNameAr not equals to UPDATED_FULL_NAME_AR
        defaultEmployeeShouldBeFound("fullNameAr.notEquals=" + UPDATED_FULL_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameArIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr in DEFAULT_FULL_NAME_AR or UPDATED_FULL_NAME_AR
        defaultEmployeeShouldBeFound("fullNameAr.in=" + DEFAULT_FULL_NAME_AR + "," + UPDATED_FULL_NAME_AR);

        // Get all the employeeList where fullNameAr equals to UPDATED_FULL_NAME_AR
        defaultEmployeeShouldNotBeFound("fullNameAr.in=" + UPDATED_FULL_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr is not null
        defaultEmployeeShouldBeFound("fullNameAr.specified=true");

        // Get all the employeeList where fullNameAr is null
        defaultEmployeeShouldNotBeFound("fullNameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByFullNameArContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr contains DEFAULT_FULL_NAME_AR
        defaultEmployeeShouldBeFound("fullNameAr.contains=" + DEFAULT_FULL_NAME_AR);

        // Get all the employeeList where fullNameAr contains UPDATED_FULL_NAME_AR
        defaultEmployeeShouldNotBeFound("fullNameAr.contains=" + UPDATED_FULL_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeesByFullNameArNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where fullNameAr does not contain DEFAULT_FULL_NAME_AR
        defaultEmployeeShouldNotBeFound("fullNameAr.doesNotContain=" + DEFAULT_FULL_NAME_AR);

        // Get all the employeeList where fullNameAr does not contain UPDATED_FULL_NAME_AR
        defaultEmployeeShouldBeFound("fullNameAr.doesNotContain=" + UPDATED_FULL_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId equals to DEFAULT_USER_ID
        defaultEmployeeShouldBeFound("userId.equals=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId equals to UPDATED_USER_ID
        defaultEmployeeShouldNotBeFound("userId.equals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId not equals to DEFAULT_USER_ID
        defaultEmployeeShouldNotBeFound("userId.notEquals=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId not equals to UPDATED_USER_ID
        defaultEmployeeShouldBeFound("userId.notEquals=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId in DEFAULT_USER_ID or UPDATED_USER_ID
        defaultEmployeeShouldBeFound("userId.in=" + DEFAULT_USER_ID + "," + UPDATED_USER_ID);

        // Get all the employeeList where userId equals to UPDATED_USER_ID
        defaultEmployeeShouldNotBeFound("userId.in=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId is not null
        defaultEmployeeShouldBeFound("userId.specified=true");

        // Get all the employeeList where userId is null
        defaultEmployeeShouldNotBeFound("userId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId is greater than or equal to DEFAULT_USER_ID
        defaultEmployeeShouldBeFound("userId.greaterThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId is greater than or equal to UPDATED_USER_ID
        defaultEmployeeShouldNotBeFound("userId.greaterThanOrEqual=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId is less than or equal to DEFAULT_USER_ID
        defaultEmployeeShouldBeFound("userId.lessThanOrEqual=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId is less than or equal to SMALLER_USER_ID
        defaultEmployeeShouldNotBeFound("userId.lessThanOrEqual=" + SMALLER_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId is less than DEFAULT_USER_ID
        defaultEmployeeShouldNotBeFound("userId.lessThan=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId is less than UPDATED_USER_ID
        defaultEmployeeShouldBeFound("userId.lessThan=" + UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void getAllEmployeesByUserIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where userId is greater than DEFAULT_USER_ID
        defaultEmployeeShouldNotBeFound("userId.greaterThan=" + DEFAULT_USER_ID);

        // Get all the employeeList where userId is greater than SMALLER_USER_ID
        defaultEmployeeShouldBeFound("userId.greaterThan=" + SMALLER_USER_ID);
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email not equals to DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email not equals to UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmployeesByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the employeeList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo is not null
        defaultEmployeeShouldBeFound("mobileNo.specified=true");

        // Get all the employeeList where mobileNo is null
        defaultEmployeeShouldNotBeFound("mobileNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeesByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo contains DEFAULT_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo contains UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    public void getAllEmployeesByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }


    @Test
    @Transactional
    public void getAllEmployeesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status equals to DEFAULT_STATUS
        defaultEmployeeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employeeList where status equals to UPDATED_STATUS
        defaultEmployeeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status not equals to DEFAULT_STATUS
        defaultEmployeeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the employeeList where status not equals to UPDATED_STATUS
        defaultEmployeeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployeeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employeeList where status equals to UPDATED_STATUS
        defaultEmployeeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is not null
        defaultEmployeeShouldBeFound("status.specified=true");

        // Get all the employeeList where status is null
        defaultEmployeeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is greater than or equal to DEFAULT_STATUS
        defaultEmployeeShouldBeFound("status.greaterThanOrEqual=" + DEFAULT_STATUS);

        // Get all the employeeList where status is greater than or equal to UPDATED_STATUS
        defaultEmployeeShouldNotBeFound("status.greaterThanOrEqual=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is less than or equal to DEFAULT_STATUS
        defaultEmployeeShouldBeFound("status.lessThanOrEqual=" + DEFAULT_STATUS);

        // Get all the employeeList where status is less than or equal to SMALLER_STATUS
        defaultEmployeeShouldNotBeFound("status.lessThanOrEqual=" + SMALLER_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is less than DEFAULT_STATUS
        defaultEmployeeShouldNotBeFound("status.lessThan=" + DEFAULT_STATUS);

        // Get all the employeeList where status is less than UPDATED_STATUS
        defaultEmployeeShouldBeFound("status.lessThan=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEmployeesByStatusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where status is greater than DEFAULT_STATUS
        defaultEmployeeShouldNotBeFound("status.greaterThan=" + DEFAULT_STATUS);

        // Get all the employeeList where status is greater than SMALLER_STATUS
        defaultEmployeeShouldBeFound("status.greaterThan=" + SMALLER_STATUS);
    }


    @Test
    @Transactional
    public void getAllEmployeesByLangKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey equals to DEFAULT_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.equals=" + DEFAULT_LANG_KEY);

        // Get all the employeeList where langKey equals to UPDATED_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.equals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLangKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey not equals to DEFAULT_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.notEquals=" + DEFAULT_LANG_KEY);

        // Get all the employeeList where langKey not equals to UPDATED_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.notEquals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLangKeyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey in DEFAULT_LANG_KEY or UPDATED_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.in=" + DEFAULT_LANG_KEY + "," + UPDATED_LANG_KEY);

        // Get all the employeeList where langKey equals to UPDATED_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.in=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void getAllEmployeesByLangKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey is not null
        defaultEmployeeShouldBeFound("langKey.specified=true");

        // Get all the employeeList where langKey is null
        defaultEmployeeShouldNotBeFound("langKey.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeVacationsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EmployeeVacation employeeVacations = EmployeeVacationResourceIT.createEntity(em);
        em.persist(employeeVacations);
        em.flush();
        employee.addEmployeeVacations(employeeVacations);
        employeeRepository.saveAndFlush(employee);
        Long employeeVacationsId = employeeVacations.getId();

        // Get all the employeeList where employeeVacations equals to employeeVacationsId
        defaultEmployeeShouldBeFound("employeeVacationsId.equals=" + employeeVacationsId);

        // Get all the employeeList where employeeVacations equals to employeeVacationsId + 1
        defaultEmployeeShouldNotBeFound("employeeVacationsId.equals=" + (employeeVacationsId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        employee.setDepartment(department);
        employeeRepository.saveAndFlush(employee);
        Long departmentId = department.getId();

        // Get all the employeeList where department equals to departmentId
        defaultEmployeeShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the employeeList where department equals to departmentId + 1
        defaultEmployeeShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByCenterIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        Center center = CenterResourceIT.createEntity(em);
        em.persist(center);
        em.flush();
        employee.setCenter(center);
        employeeRepository.saveAndFlush(employee);
        Long centerId = center.getId();

        // Get all the employeeList where center equals to centerId
        defaultEmployeeShouldBeFound("centerId.equals=" + centerId);

        // Get all the employeeList where center equals to centerId + 1
        defaultEmployeeShouldNotBeFound("centerId.equals=" + (centerId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployeesByEmployeeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        EmployeeType employeeType = EmployeeTypeResourceIT.createEntity(em);
        em.persist(employeeType);
        em.flush();
        employee.setEmployeeType(employeeType);
        employeeRepository.saveAndFlush(employee);
        Long employeeTypeId = employeeType.getId();

        // Get all the employeeList where employeeType equals to employeeTypeId
        defaultEmployeeShouldBeFound("employeeTypeId.equals=" + employeeTypeId);

        // Get all the employeeList where employeeType equals to employeeTypeId + 1
        defaultEmployeeShouldNotBeFound("employeeTypeId.equals=" + (employeeTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullNameEn").value(hasItem(DEFAULT_FULL_NAME_EN)))
            .andExpect(jsonPath("$.[*].fullNameAr").value(hasItem(DEFAULT_FULL_NAME_AR)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .fullNameEn(UPDATED_FULL_NAME_EN)
            .fullNameAr(UPDATED_FULL_NAME_AR)
            .userId(UPDATED_USER_ID)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .status(UPDATED_STATUS)
            .langKey(UPDATED_LANG_KEY);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFullNameEn()).isEqualTo(UPDATED_FULL_NAME_EN);
        assertThat(testEmployee.getFullNameAr()).isEqualTo(UPDATED_FULL_NAME_AR);
        assertThat(testEmployee.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEmployee.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployee.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
