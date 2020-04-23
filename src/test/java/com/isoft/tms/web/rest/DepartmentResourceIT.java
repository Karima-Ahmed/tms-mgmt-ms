package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.Department;
import com.isoft.tms.repository.DepartmentRepository;
import com.isoft.tms.service.DepartmentService;
import com.isoft.tms.service.dto.DepartmentDTO;
import com.isoft.tms.service.mapper.DepartmentMapper;
import com.isoft.tms.service.dto.DepartmentCriteria;
import com.isoft.tms.service.DepartmentQueryService;

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
 * Integration tests for the {@link DepartmentResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class DepartmentResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentQueryService departmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartmentMockMvc;

    private Department department;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createEntity(EntityManager em) {
        Department department = new Department()
            .nameEn(DEFAULT_NAME_EN)
            .nameAr(DEFAULT_NAME_AR);
        return department;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Department createUpdatedEntity(EntityManager em) {
        Department department = new Department()
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        return department;
    }

    @BeforeEach
    public void initTest() {
        department = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartment() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);
        restDepartmentMockMvc.perform(post("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testDepartment.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
    }

    @Test
    @Transactional
    public void createDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department with an existing ID
        department.setId(1L);
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentMockMvc.perform(post("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentRepository.findAll().size();
        // set the field null
        department.setNameEn(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        restDepartmentMockMvc.perform(post("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentRepository.findAll().size();
        // set the field null
        department.setNameAr(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        restDepartmentMockMvc.perform(post("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartments() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));
    }
    
    @Test
    @Transactional
    public void getDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR));
    }


    @Test
    @Transactional
    public void getDepartmentsByIdFiltering() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        Long id = department.getId();

        defaultDepartmentShouldBeFound("id.equals=" + id);
        defaultDepartmentShouldNotBeFound("id.notEquals=" + id);

        defaultDepartmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepartmentShouldNotBeFound("id.greaterThan=" + id);

        defaultDepartmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepartmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDepartmentsByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn equals to DEFAULT_NAME_EN
        defaultDepartmentShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the departmentList where nameEn equals to UPDATED_NAME_EN
        defaultDepartmentShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn not equals to DEFAULT_NAME_EN
        defaultDepartmentShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the departmentList where nameEn not equals to UPDATED_NAME_EN
        defaultDepartmentShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultDepartmentShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the departmentList where nameEn equals to UPDATED_NAME_EN
        defaultDepartmentShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn is not null
        defaultDepartmentShouldBeFound("nameEn.specified=true");

        // Get all the departmentList where nameEn is null
        defaultDepartmentShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllDepartmentsByNameEnContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn contains DEFAULT_NAME_EN
        defaultDepartmentShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the departmentList where nameEn contains UPDATED_NAME_EN
        defaultDepartmentShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameEn does not contain DEFAULT_NAME_EN
        defaultDepartmentShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the departmentList where nameEn does not contain UPDATED_NAME_EN
        defaultDepartmentShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllDepartmentsByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr equals to DEFAULT_NAME_AR
        defaultDepartmentShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the departmentList where nameAr equals to UPDATED_NAME_AR
        defaultDepartmentShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr not equals to DEFAULT_NAME_AR
        defaultDepartmentShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the departmentList where nameAr not equals to UPDATED_NAME_AR
        defaultDepartmentShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultDepartmentShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the departmentList where nameAr equals to UPDATED_NAME_AR
        defaultDepartmentShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr is not null
        defaultDepartmentShouldBeFound("nameAr.specified=true");

        // Get all the departmentList where nameAr is null
        defaultDepartmentShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllDepartmentsByNameArContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr contains DEFAULT_NAME_AR
        defaultDepartmentShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the departmentList where nameAr contains UPDATED_NAME_AR
        defaultDepartmentShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllDepartmentsByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departmentList where nameAr does not contain DEFAULT_NAME_AR
        defaultDepartmentShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the departmentList where nameAr does not contain UPDATED_NAME_AR
        defaultDepartmentShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartmentShouldBeFound(String filter) throws Exception {
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)));

        // Check, that the count call also returns 1
        restDepartmentMockMvc.perform(get("/api/departments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartmentShouldNotBeFound(String filter) throws Exception {
        restDepartmentMockMvc.perform(get("/api/departments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartmentMockMvc.perform(get("/api/departments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department
        Department updatedDepartment = departmentRepository.findById(department.getId()).get();
        // Disconnect from session so that the updates on updatedDepartment are not directly saved in db
        em.detach(updatedDepartment);
        updatedDepartment
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR);
        DepartmentDTO departmentDTO = departmentMapper.toDto(updatedDepartment);

        restDepartmentMockMvc.perform(put("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departmentList.get(departmentList.size() - 1);
        assertThat(testDepartment.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testDepartment.getNameAr()).isEqualTo(UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartment() throws Exception {
        int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.toDto(department);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentMockMvc.perform(put("/api/departments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Department in the database
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        int databaseSizeBeforeDelete = departmentRepository.findAll().size();

        // Delete the department
        restDepartmentMockMvc.perform(delete("/api/departments/{id}", department.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Department> departmentList = departmentRepository.findAll();
        assertThat(departmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
