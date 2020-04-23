package com.isoft.tms.web.rest;

import com.isoft.tms.TrainingManagementSystemApp;
import com.isoft.tms.config.SecurityBeanOverrideConfiguration;
import com.isoft.tms.domain.CenterWorkingDays;
import com.isoft.tms.domain.WorkingDays;
import com.isoft.tms.repository.CenterWorkingDaysRepository;
import com.isoft.tms.service.CenterWorkingDaysService;
import com.isoft.tms.service.dto.CenterWorkingDaysDTO;
import com.isoft.tms.service.mapper.CenterWorkingDaysMapper;
import com.isoft.tms.service.dto.CenterWorkingDaysCriteria;
import com.isoft.tms.service.CenterWorkingDaysQueryService;

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
 * Integration tests for the {@link CenterWorkingDaysResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, TrainingManagementSystemApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class CenterWorkingDaysResourceIT {

    private static final Instant DEFAULT_START_WORKING_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_WORKING_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_WORKING_HOUR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_WORKING_HOUR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CenterWorkingDaysRepository centerWorkingDaysRepository;

    @Autowired
    private CenterWorkingDaysMapper centerWorkingDaysMapper;

    @Autowired
    private CenterWorkingDaysService centerWorkingDaysService;

    @Autowired
    private CenterWorkingDaysQueryService centerWorkingDaysQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCenterWorkingDaysMockMvc;

    private CenterWorkingDays centerWorkingDays;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterWorkingDays createEntity(EntityManager em) {
        CenterWorkingDays centerWorkingDays = new CenterWorkingDays()
            .startWorkingHour(DEFAULT_START_WORKING_HOUR)
            .endWorkingHour(DEFAULT_END_WORKING_HOUR);
        return centerWorkingDays;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterWorkingDays createUpdatedEntity(EntityManager em) {
        CenterWorkingDays centerWorkingDays = new CenterWorkingDays()
            .startWorkingHour(UPDATED_START_WORKING_HOUR)
            .endWorkingHour(UPDATED_END_WORKING_HOUR);
        return centerWorkingDays;
    }

    @BeforeEach
    public void initTest() {
        centerWorkingDays = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenterWorkingDays() throws Exception {
        int databaseSizeBeforeCreate = centerWorkingDaysRepository.findAll().size();

        // Create the CenterWorkingDays
        CenterWorkingDaysDTO centerWorkingDaysDTO = centerWorkingDaysMapper.toDto(centerWorkingDays);
        restCenterWorkingDaysMockMvc.perform(post("/api/center-working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerWorkingDaysDTO)))
            .andExpect(status().isCreated());

        // Validate the CenterWorkingDays in the database
        List<CenterWorkingDays> centerWorkingDaysList = centerWorkingDaysRepository.findAll();
        assertThat(centerWorkingDaysList).hasSize(databaseSizeBeforeCreate + 1);
        CenterWorkingDays testCenterWorkingDays = centerWorkingDaysList.get(centerWorkingDaysList.size() - 1);
        assertThat(testCenterWorkingDays.getStartWorkingHour()).isEqualTo(DEFAULT_START_WORKING_HOUR);
        assertThat(testCenterWorkingDays.getEndWorkingHour()).isEqualTo(DEFAULT_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void createCenterWorkingDaysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centerWorkingDaysRepository.findAll().size();

        // Create the CenterWorkingDays with an existing ID
        centerWorkingDays.setId(1L);
        CenterWorkingDaysDTO centerWorkingDaysDTO = centerWorkingDaysMapper.toDto(centerWorkingDays);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterWorkingDaysMockMvc.perform(post("/api/center-working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerWorkingDaysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CenterWorkingDays in the database
        List<CenterWorkingDays> centerWorkingDaysList = centerWorkingDaysRepository.findAll();
        assertThat(centerWorkingDaysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCenterWorkingDays() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerWorkingDays.getId().intValue())))
            .andExpect(jsonPath("$.[*].startWorkingHour").value(hasItem(DEFAULT_START_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endWorkingHour").value(hasItem(DEFAULT_END_WORKING_HOUR.toString())));
    }
    
    @Test
    @Transactional
    public void getCenterWorkingDays() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get the centerWorkingDays
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days/{id}", centerWorkingDays.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centerWorkingDays.getId().intValue()))
            .andExpect(jsonPath("$.startWorkingHour").value(DEFAULT_START_WORKING_HOUR.toString()))
            .andExpect(jsonPath("$.endWorkingHour").value(DEFAULT_END_WORKING_HOUR.toString()));
    }


    @Test
    @Transactional
    public void getCenterWorkingDaysByIdFiltering() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        Long id = centerWorkingDays.getId();

        defaultCenterWorkingDaysShouldBeFound("id.equals=" + id);
        defaultCenterWorkingDaysShouldNotBeFound("id.notEquals=" + id);

        defaultCenterWorkingDaysShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCenterWorkingDaysShouldNotBeFound("id.greaterThan=" + id);

        defaultCenterWorkingDaysShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCenterWorkingDaysShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCenterWorkingDaysByStartWorkingHourIsEqualToSomething() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where startWorkingHour equals to DEFAULT_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("startWorkingHour.equals=" + DEFAULT_START_WORKING_HOUR);

        // Get all the centerWorkingDaysList where startWorkingHour equals to UPDATED_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("startWorkingHour.equals=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByStartWorkingHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where startWorkingHour not equals to DEFAULT_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("startWorkingHour.notEquals=" + DEFAULT_START_WORKING_HOUR);

        // Get all the centerWorkingDaysList where startWorkingHour not equals to UPDATED_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("startWorkingHour.notEquals=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByStartWorkingHourIsInShouldWork() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where startWorkingHour in DEFAULT_START_WORKING_HOUR or UPDATED_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("startWorkingHour.in=" + DEFAULT_START_WORKING_HOUR + "," + UPDATED_START_WORKING_HOUR);

        // Get all the centerWorkingDaysList where startWorkingHour equals to UPDATED_START_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("startWorkingHour.in=" + UPDATED_START_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByStartWorkingHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where startWorkingHour is not null
        defaultCenterWorkingDaysShouldBeFound("startWorkingHour.specified=true");

        // Get all the centerWorkingDaysList where startWorkingHour is null
        defaultCenterWorkingDaysShouldNotBeFound("startWorkingHour.specified=false");
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByEndWorkingHourIsEqualToSomething() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where endWorkingHour equals to DEFAULT_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("endWorkingHour.equals=" + DEFAULT_END_WORKING_HOUR);

        // Get all the centerWorkingDaysList where endWorkingHour equals to UPDATED_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("endWorkingHour.equals=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByEndWorkingHourIsNotEqualToSomething() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where endWorkingHour not equals to DEFAULT_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("endWorkingHour.notEquals=" + DEFAULT_END_WORKING_HOUR);

        // Get all the centerWorkingDaysList where endWorkingHour not equals to UPDATED_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("endWorkingHour.notEquals=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByEndWorkingHourIsInShouldWork() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where endWorkingHour in DEFAULT_END_WORKING_HOUR or UPDATED_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldBeFound("endWorkingHour.in=" + DEFAULT_END_WORKING_HOUR + "," + UPDATED_END_WORKING_HOUR);

        // Get all the centerWorkingDaysList where endWorkingHour equals to UPDATED_END_WORKING_HOUR
        defaultCenterWorkingDaysShouldNotBeFound("endWorkingHour.in=" + UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByEndWorkingHourIsNullOrNotNull() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        // Get all the centerWorkingDaysList where endWorkingHour is not null
        defaultCenterWorkingDaysShouldBeFound("endWorkingHour.specified=true");

        // Get all the centerWorkingDaysList where endWorkingHour is null
        defaultCenterWorkingDaysShouldNotBeFound("endWorkingHour.specified=false");
    }

    @Test
    @Transactional
    public void getAllCenterWorkingDaysByWorkingDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);
        WorkingDays workingDays = WorkingDaysResourceIT.createEntity(em);
        em.persist(workingDays);
        em.flush();
        centerWorkingDays.addWorkingDays(workingDays);
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);
        Long workingDaysId = workingDays.getId();

        // Get all the centerWorkingDaysList where workingDays equals to workingDaysId
        defaultCenterWorkingDaysShouldBeFound("workingDaysId.equals=" + workingDaysId);

        // Get all the centerWorkingDaysList where workingDays equals to workingDaysId + 1
        defaultCenterWorkingDaysShouldNotBeFound("workingDaysId.equals=" + (workingDaysId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCenterWorkingDaysShouldBeFound(String filter) throws Exception {
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerWorkingDays.getId().intValue())))
            .andExpect(jsonPath("$.[*].startWorkingHour").value(hasItem(DEFAULT_START_WORKING_HOUR.toString())))
            .andExpect(jsonPath("$.[*].endWorkingHour").value(hasItem(DEFAULT_END_WORKING_HOUR.toString())));

        // Check, that the count call also returns 1
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCenterWorkingDaysShouldNotBeFound(String filter) throws Exception {
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCenterWorkingDays() throws Exception {
        // Get the centerWorkingDays
        restCenterWorkingDaysMockMvc.perform(get("/api/center-working-days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenterWorkingDays() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        int databaseSizeBeforeUpdate = centerWorkingDaysRepository.findAll().size();

        // Update the centerWorkingDays
        CenterWorkingDays updatedCenterWorkingDays = centerWorkingDaysRepository.findById(centerWorkingDays.getId()).get();
        // Disconnect from session so that the updates on updatedCenterWorkingDays are not directly saved in db
        em.detach(updatedCenterWorkingDays);
        updatedCenterWorkingDays
            .startWorkingHour(UPDATED_START_WORKING_HOUR)
            .endWorkingHour(UPDATED_END_WORKING_HOUR);
        CenterWorkingDaysDTO centerWorkingDaysDTO = centerWorkingDaysMapper.toDto(updatedCenterWorkingDays);

        restCenterWorkingDaysMockMvc.perform(put("/api/center-working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerWorkingDaysDTO)))
            .andExpect(status().isOk());

        // Validate the CenterWorkingDays in the database
        List<CenterWorkingDays> centerWorkingDaysList = centerWorkingDaysRepository.findAll();
        assertThat(centerWorkingDaysList).hasSize(databaseSizeBeforeUpdate);
        CenterWorkingDays testCenterWorkingDays = centerWorkingDaysList.get(centerWorkingDaysList.size() - 1);
        assertThat(testCenterWorkingDays.getStartWorkingHour()).isEqualTo(UPDATED_START_WORKING_HOUR);
        assertThat(testCenterWorkingDays.getEndWorkingHour()).isEqualTo(UPDATED_END_WORKING_HOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingCenterWorkingDays() throws Exception {
        int databaseSizeBeforeUpdate = centerWorkingDaysRepository.findAll().size();

        // Create the CenterWorkingDays
        CenterWorkingDaysDTO centerWorkingDaysDTO = centerWorkingDaysMapper.toDto(centerWorkingDays);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterWorkingDaysMockMvc.perform(put("/api/center-working-days").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(centerWorkingDaysDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CenterWorkingDays in the database
        List<CenterWorkingDays> centerWorkingDaysList = centerWorkingDaysRepository.findAll();
        assertThat(centerWorkingDaysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenterWorkingDays() throws Exception {
        // Initialize the database
        centerWorkingDaysRepository.saveAndFlush(centerWorkingDays);

        int databaseSizeBeforeDelete = centerWorkingDaysRepository.findAll().size();

        // Delete the centerWorkingDays
        restCenterWorkingDaysMockMvc.perform(delete("/api/center-working-days/{id}", centerWorkingDays.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CenterWorkingDays> centerWorkingDaysList = centerWorkingDaysRepository.findAll();
        assertThat(centerWorkingDaysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
