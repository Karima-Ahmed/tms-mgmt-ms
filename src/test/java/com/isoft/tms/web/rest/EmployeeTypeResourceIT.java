package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.EmployeeType;
import com.isoft.tms.repository.EmployeeTypeRepository;
import com.isoft.tms.service.EmployeeTypeService;
import com.isoft.tms.service.dto.EmployeeTypeDTO;
import com.isoft.tms.service.mapper.EmployeeTypeMapper;
import com.isoft.tms.service.dto.EmployeeTypeCriteria;
import com.isoft.tms.service.EmployeeTypeQueryService;

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

/**
 * Integration tests for the {@link EmployeeTypeResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class EmployeeTypeResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    private EmployeeTypeMapper employeeTypeMapper;

    @Autowired
    private EmployeeTypeService employeeTypeService;

    @Autowired
    private EmployeeTypeQueryService employeeTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeTypeMockMvc;

    private EmployeeType employeeType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeType createEntity(EntityManager em) {
        EmployeeType employeeType = new EmployeeType()
            .nameEn(DEFAULT_NAME_EN)
            .nameAr(DEFAULT_NAME_AR);
        return employeeType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeType createUpdatedEntity(EntityManager em) {
        EmployeeType employeeType = new EmployeeType()
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        return employeeType;
    }

    @BeforeEach
    public void initTest() {
        employeeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeType() throws Exception {
        int databaseSizeBeforeCreate = employeeTypeRepository.findAll().size();

        // Create the EmployeeType
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(employeeType);
        restEmployeeTypeMockMvc.perform(post("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeType in the database
        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeType testEmployeeType = employeeTypeList.get(employeeTypeList.size() - 1);
        assertThat(testEmployeeType.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testEmployeeType.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
    }

    @Test
    @Transactional
    public void createEmployeeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeTypeRepository.findAll().size();

        // Create the EmployeeType with an existing ID
        employeeType.setId(1L);
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(employeeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeTypeMockMvc.perform(post("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeType in the database
        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeTypeRepository.findAll().size();
        // set the field null
        employeeType.setNameEn(null);

        // Create the EmployeeType, which fails.
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(employeeType);

        restEmployeeTypeMockMvc.perform(post("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeTypeRepository.findAll().size();
        // set the field null
        employeeType.setNameAr(null);

        // Create the EmployeeType, which fails.
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(employeeType);

        restEmployeeTypeMockMvc.perform(post("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypes() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList
        restEmployeeTypeMockMvc.perform(get("/api/employee-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));
    }
    
    @Test
    @Transactional
    public void getEmployeeType() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get the employeeType
        restEmployeeTypeMockMvc.perform(get("/api/employee-types/{id}", employeeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeType.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR));
    }


    @Test
    @Transactional
    public void getEmployeeTypesByIdFiltering() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        Long id = employeeType.getId();

        defaultEmployeeTypeShouldBeFound("id.equals=" + id);
        defaultEmployeeTypeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn equals to DEFAULT_NAME_EN
        defaultEmployeeTypeShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the employeeTypeList where nameEn equals to UPDATED_NAME_EN
        defaultEmployeeTypeShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn not equals to DEFAULT_NAME_EN
        defaultEmployeeTypeShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the employeeTypeList where nameEn not equals to UPDATED_NAME_EN
        defaultEmployeeTypeShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultEmployeeTypeShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the employeeTypeList where nameEn equals to UPDATED_NAME_EN
        defaultEmployeeTypeShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn is not null
        defaultEmployeeTypeShouldBeFound("nameEn.specified=true");

        // Get all the employeeTypeList where nameEn is null
        defaultEmployeeTypeShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnContainsSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn contains DEFAULT_NAME_EN
        defaultEmployeeTypeShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the employeeTypeList where nameEn contains UPDATED_NAME_EN
        defaultEmployeeTypeShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameEn does not contain DEFAULT_NAME_EN
        defaultEmployeeTypeShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the employeeTypeList where nameEn does not contain UPDATED_NAME_EN
        defaultEmployeeTypeShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllEmployeeTypesByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr equals to DEFAULT_NAME_AR
        defaultEmployeeTypeShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the employeeTypeList where nameAr equals to UPDATED_NAME_AR
        defaultEmployeeTypeShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr not equals to DEFAULT_NAME_AR
        defaultEmployeeTypeShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the employeeTypeList where nameAr not equals to UPDATED_NAME_AR
        defaultEmployeeTypeShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultEmployeeTypeShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the employeeTypeList where nameAr equals to UPDATED_NAME_AR
        defaultEmployeeTypeShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr is not null
        defaultEmployeeTypeShouldBeFound("nameAr.specified=true");

        // Get all the employeeTypeList where nameAr is null
        defaultEmployeeTypeShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployeeTypesByNameArContainsSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr contains DEFAULT_NAME_AR
        defaultEmployeeTypeShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the employeeTypeList where nameAr contains UPDATED_NAME_AR
        defaultEmployeeTypeShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllEmployeeTypesByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        // Get all the employeeTypeList where nameAr does not contain DEFAULT_NAME_AR
        defaultEmployeeTypeShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the employeeTypeList where nameAr does not contain UPDATED_NAME_AR
        defaultEmployeeTypeShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeTypeShouldBeFound(String filter) throws Exception {
        restEmployeeTypeMockMvc.perform(get("/api/employee-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));

        // Check, that the count call also returns 1
        restEmployeeTypeMockMvc.perform(get("/api/employee-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeTypeShouldNotBeFound(String filter) throws Exception {
        restEmployeeTypeMockMvc.perform(get("/api/employee-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeTypeMockMvc.perform(get("/api/employee-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployeeType() throws Exception {
        // Get the employeeType
        restEmployeeTypeMockMvc.perform(get("/api/employee-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeType() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        int databaseSizeBeforeUpdate = employeeTypeRepository.findAll().size();

        // Update the employeeType
        EmployeeType updatedEmployeeType = employeeTypeRepository.findById(employeeType.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeType are not directly saved in db
        em.detach(updatedEmployeeType);
        updatedEmployeeType
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(updatedEmployeeType);

        restEmployeeTypeMockMvc.perform(put("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeType in the database
        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeUpdate);
        EmployeeType testEmployeeType = employeeTypeList.get(employeeTypeList.size() - 1);
        assertThat(testEmployeeType.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testEmployeeType.getNameAr()).isEqualTo(UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeType() throws Exception {
        int databaseSizeBeforeUpdate = employeeTypeRepository.findAll().size();

        // Create the EmployeeType
        EmployeeTypeDTO employeeTypeDTO = employeeTypeMapper.toDto(employeeType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeTypeMockMvc.perform(put("/api/employee-types").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeType in the database
        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeType() throws Exception {
        // Initialize the database
        employeeTypeRepository.saveAndFlush(employeeType);

        int databaseSizeBeforeDelete = employeeTypeRepository.findAll().size();

        // Delete the employeeType
        restEmployeeTypeMockMvc.perform(delete("/api/employee-types/{id}", employeeType.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeType> employeeTypeList = employeeTypeRepository.findAll();
        assertThat(employeeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
