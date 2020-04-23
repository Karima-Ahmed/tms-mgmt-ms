package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.WorkingDays;
import com.isoft.tms.domain.CenterWorkingDays;
import com.isoft.tms.repository.WorkingDaysRepository;
import com.isoft.tms.service.WorkingDaysService;
import com.isoft.tms.service.dto.WorkingDaysDTO;
import com.isoft.tms.service.mapper.WorkingDaysMapper;
import com.isoft.tms.service.dto.WorkingDaysCriteria;
import com.isoft.tms.service.WorkingDaysQueryService;

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
 * Integration tests for the {@link WorkingDaysResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class WorkingDaysResourceIT {

    private static final String DEFAULT_NAME_EN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_AR = "AAAAAAAAAA";
    private static final String UPDATED_NAME_AR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private WorkingDaysRepository workingDaysRepository;

    @Autowired
    private WorkingDaysMapper workingDaysMapper;

    @Autowired
    private WorkingDaysService workingDaysService;

    @Autowired
    private WorkingDaysQueryService workingDaysQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingDaysMockMvc;

    private WorkingDays workingDays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingDays createEntity(EntityManager em) {
        WorkingDays workingDays = new WorkingDays()
            .nameEn(DEFAULT_NAME_EN)
            .nameAr(DEFAULT_NAME_AR)
            .status(DEFAULT_STATUS);
        return workingDays;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingDays createUpdatedEntity(EntityManager em) {
        WorkingDays workingDays = new WorkingDays()
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR)
            .status(UPDATED_STATUS);
        return workingDays;
    }

    @BeforeEach
    public void initTest() {
        workingDays = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkingDays() throws Exception {
        int databaseSizeBeforeCreate = workingDaysRepository.findAll().size();

        // Create the WorkingDays
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(workingDays);
        restWorkingDaysMockMvc.perform(post("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkingDays in the database
        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingDays testWorkingDays = workingDaysList.get(workingDaysList.size() - 1);
        assertThat(testWorkingDays.getNameEn()).isEqualTo(DEFAULT_NAME_EN);
        assertThat(testWorkingDays.getNameAr()).isEqualTo(DEFAULT_NAME_AR);
        assertThat(testWorkingDays.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createWorkingDaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workingDaysRepository.findAll().size();

        // Create the WorkingDays with an existing ID
        workingDays.setId(1L);
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(workingDays);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingDaysMockMvc.perform(post("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingDays in the database
        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingDaysRepository.findAll().size();
        // set the field null
        workingDays.setNameEn(null);

        // Create the WorkingDays, which fails.
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(workingDays);

        restWorkingDaysMockMvc.perform(post("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameArIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingDaysRepository.findAll().size();
        // set the field null
        workingDays.setNameAr(null);

        // Create the WorkingDays, which fails.
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(workingDays);

        restWorkingDaysMockMvc.perform(post("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isBadRequest());

        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkingDays() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList
        restWorkingDaysMockMvc.perform(get("/api/working-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingDays.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getWorkingDays() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get the workingDays
        restWorkingDaysMockMvc.perform(get("/api/working-days/{id}", workingDays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingDays.getId().intValue()))
            .andExpect(jsonPath("$.nameEn").value(DEFAULT_NAME_EN))
            .andExpect(jsonPath("$.nameAr").value(DEFAULT_NAME_AR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }


    @Test
    @Transactional
    public void getWorkingDaysByIdFiltering() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        Long id = workingDays.getId();

        defaultWorkingDaysShouldBeFound("id.equals=" + id);
        defaultWorkingDaysShouldNotBeFound("id.notEquals=" + id);

        defaultWorkingDaysShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkingDaysShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkingDaysShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkingDaysShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkingDaysByNameEnIsEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn equals to DEFAULT_NAME_EN
        defaultWorkingDaysShouldBeFound("nameEn.equals=" + DEFAULT_NAME_EN);

        // Get all the workingDaysList where nameEn equals to UPDATED_NAME_EN
        defaultWorkingDaysShouldNotBeFound("nameEn.equals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn not equals to DEFAULT_NAME_EN
        defaultWorkingDaysShouldNotBeFound("nameEn.notEquals=" + DEFAULT_NAME_EN);

        // Get all the workingDaysList where nameEn not equals to UPDATED_NAME_EN
        defaultWorkingDaysShouldBeFound("nameEn.notEquals=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameEnIsInShouldWork() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn in DEFAULT_NAME_EN or UPDATED_NAME_EN
        defaultWorkingDaysShouldBeFound("nameEn.in=" + DEFAULT_NAME_EN + "," + UPDATED_NAME_EN);

        // Get all the workingDaysList where nameEn equals to UPDATED_NAME_EN
        defaultWorkingDaysShouldNotBeFound("nameEn.in=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn is not null
        defaultWorkingDaysShouldBeFound("nameEn.specified=true");

        // Get all the workingDaysList where nameEn is null
        defaultWorkingDaysShouldNotBeFound("nameEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkingDaysByNameEnContainsSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn contains DEFAULT_NAME_EN
        defaultWorkingDaysShouldBeFound("nameEn.contains=" + DEFAULT_NAME_EN);

        // Get all the workingDaysList where nameEn contains UPDATED_NAME_EN
        defaultWorkingDaysShouldNotBeFound("nameEn.contains=" + UPDATED_NAME_EN);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameEnNotContainsSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameEn does not contain DEFAULT_NAME_EN
        defaultWorkingDaysShouldNotBeFound("nameEn.doesNotContain=" + DEFAULT_NAME_EN);

        // Get all the workingDaysList where nameEn does not contain UPDATED_NAME_EN
        defaultWorkingDaysShouldBeFound("nameEn.doesNotContain=" + UPDATED_NAME_EN);
    }


    @Test
    @Transactional
    public void getAllWorkingDaysByNameArIsEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr equals to DEFAULT_NAME_AR
        defaultWorkingDaysShouldBeFound("nameAr.equals=" + DEFAULT_NAME_AR);

        // Get all the workingDaysList where nameAr equals to UPDATED_NAME_AR
        defaultWorkingDaysShouldNotBeFound("nameAr.equals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameArIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr not equals to DEFAULT_NAME_AR
        defaultWorkingDaysShouldNotBeFound("nameAr.notEquals=" + DEFAULT_NAME_AR);

        // Get all the workingDaysList where nameAr not equals to UPDATED_NAME_AR
        defaultWorkingDaysShouldBeFound("nameAr.notEquals=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameArIsInShouldWork() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr in DEFAULT_NAME_AR or UPDATED_NAME_AR
        defaultWorkingDaysShouldBeFound("nameAr.in=" + DEFAULT_NAME_AR + "," + UPDATED_NAME_AR);

        // Get all the workingDaysList where nameAr equals to UPDATED_NAME_AR
        defaultWorkingDaysShouldNotBeFound("nameAr.in=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameArIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr is not null
        defaultWorkingDaysShouldBeFound("nameAr.specified=true");

        // Get all the workingDaysList where nameAr is null
        defaultWorkingDaysShouldNotBeFound("nameAr.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkingDaysByNameArContainsSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr contains DEFAULT_NAME_AR
        defaultWorkingDaysShouldBeFound("nameAr.contains=" + DEFAULT_NAME_AR);

        // Get all the workingDaysList where nameAr contains UPDATED_NAME_AR
        defaultWorkingDaysShouldNotBeFound("nameAr.contains=" + UPDATED_NAME_AR);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByNameArNotContainsSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where nameAr does not contain DEFAULT_NAME_AR
        defaultWorkingDaysShouldNotBeFound("nameAr.doesNotContain=" + DEFAULT_NAME_AR);

        // Get all the workingDaysList where nameAr does not contain UPDATED_NAME_AR
        defaultWorkingDaysShouldBeFound("nameAr.doesNotContain=" + UPDATED_NAME_AR);
    }


    @Test
    @Transactional
    public void getAllWorkingDaysByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where status equals to DEFAULT_STATUS
        defaultWorkingDaysShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the workingDaysList where status equals to UPDATED_STATUS
        defaultWorkingDaysShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where status not equals to DEFAULT_STATUS
        defaultWorkingDaysShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the workingDaysList where status not equals to UPDATED_STATUS
        defaultWorkingDaysShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultWorkingDaysShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the workingDaysList where status equals to UPDATED_STATUS
        defaultWorkingDaysShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        // Get all the workingDaysList where status is not null
        defaultWorkingDaysShouldBeFound("status.specified=true");

        // Get all the workingDaysList where status is null
        defaultWorkingDaysShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkingDaysByCenterWorkingDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);
        CenterWorkingDays centerWorkingDays = CenterWorkingDaysResourceIT.createEntity(em);
        em.persist(centerWorkingDays);
        em.flush();
        workingDays.setCenterWorkingDays(centerWorkingDays);
        workingDaysRepository.saveAndFlush(workingDays);
        Long centerWorkingDaysId = centerWorkingDays.getId();

        // Get all the workingDaysList where centerWorkingDays equals to centerWorkingDaysId
        defaultWorkingDaysShouldBeFound("centerWorkingDaysId.equals=" + centerWorkingDaysId);

        // Get all the workingDaysList where centerWorkingDays equals to centerWorkingDaysId + 1
        defaultWorkingDaysShouldNotBeFound("centerWorkingDaysId.equals=" + (centerWorkingDaysId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkingDaysShouldBeFound(String filter) throws Exception {
        restWorkingDaysMockMvc.perform(get("/api/working-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingDays.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(DEFAULT_NAME_EN)))
            .andExpect(jsonPath("$.[*].nameAr").value(hasItem(DEFAULT_NAME_AR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));

        // Check, that the count call also returns 1
        restWorkingDaysMockMvc.perform(get("/api/working-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkingDaysShouldNotBeFound(String filter) throws Exception {
        restWorkingDaysMockMvc.perform(get("/api/working-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkingDaysMockMvc.perform(get("/api/working-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWorkingDays() throws Exception {
        // Get the workingDays
        restWorkingDaysMockMvc.perform(get("/api/working-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkingDays() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        int databaseSizeBeforeUpdate = workingDaysRepository.findAll().size();

        // Update the workingDays
        WorkingDays updatedWorkingDays = workingDaysRepository.findById(workingDays.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingDays are not directly saved in db
        em.detach(updatedWorkingDays);
        updatedWorkingDays
            .nameEn(UPDATED_NAME_EN)
            .nameAr(UPDATED_NAME_AR)
            .status(UPDATED_STATUS);
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(updatedWorkingDays);

        restWorkingDaysMockMvc.perform(put("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isOk());

        // Validate the WorkingDays in the database
        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeUpdate);
        WorkingDays testWorkingDays = workingDaysList.get(workingDaysList.size() - 1);
        assertThat(testWorkingDays.getNameEn()).isEqualTo(UPDATED_NAME_EN);
        assertThat(testWorkingDays.getNameAr()).isEqualTo(UPDATED_NAME_AR);
        assertThat(testWorkingDays.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkingDays() throws Exception {
        int databaseSizeBeforeUpdate = workingDaysRepository.findAll().size();

        // Create the WorkingDays
        WorkingDaysDTO workingDaysDTO = workingDaysMapper.toDto(workingDays);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingDaysMockMvc.perform(put("/api/working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workingDaysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkingDays in the database
        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkingDays() throws Exception {
        // Initialize the database
        workingDaysRepository.saveAndFlush(workingDays);

        int databaseSizeBeforeDelete = workingDaysRepository.findAll().size();

        // Delete the workingDays
        restWorkingDaysMockMvc.perform(delete("/api/working-days/{id}", workingDays.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingDays> workingDaysList = workingDaysRepository.findAll();
        assertThat(workingDaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
