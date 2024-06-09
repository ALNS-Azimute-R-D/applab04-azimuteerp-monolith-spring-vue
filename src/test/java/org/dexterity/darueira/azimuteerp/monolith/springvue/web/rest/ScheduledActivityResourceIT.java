package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivityAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ScheduledActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ScheduledActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ScheduledActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ScheduledActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ScheduledActivityMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScheduledActivityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ScheduledActivityResourceIT {

    private static final String DEFAULT_CUSTOMIZED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMIZED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NUMBER_OF_ATTENDEES = 1;
    private static final Integer UPDATED_NUMBER_OF_ATTENDEES = 2;

    private static final Integer DEFAULT_AVERAGE_EVALUATION_IN_STARS = 1;
    private static final Integer UPDATED_AVERAGE_EVALUATION_IN_STARS = 2;

    private static final String DEFAULT_CUSTOM_ATTRIBUTTES_DETAILS_JSON = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOM_ATTRIBUTTES_DETAILS_JSON = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/scheduled-activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ScheduledActivityRepository scheduledActivityRepository;

    @Mock
    private ScheduledActivityRepository scheduledActivityRepositoryMock;

    @Autowired
    private ScheduledActivityMapper scheduledActivityMapper;

    @Mock
    private ScheduledActivityService scheduledActivityServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduledActivityMockMvc;

    private ScheduledActivity scheduledActivity;

    private ScheduledActivity insertedScheduledActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduledActivity createEntity(EntityManager em) {
        ScheduledActivity scheduledActivity = new ScheduledActivity()
            .customizedName(DEFAULT_CUSTOMIZED_NAME)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .numberOfAttendees(DEFAULT_NUMBER_OF_ATTENDEES)
            .averageEvaluationInStars(DEFAULT_AVERAGE_EVALUATION_IN_STARS)
            .customAttributtesDetailsJSON(DEFAULT_CUSTOM_ATTRIBUTTES_DETAILS_JSON)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return scheduledActivity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduledActivity createUpdatedEntity(EntityManager em) {
        ScheduledActivity scheduledActivity = new ScheduledActivity()
            .customizedName(UPDATED_CUSTOMIZED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .numberOfAttendees(UPDATED_NUMBER_OF_ATTENDEES)
            .averageEvaluationInStars(UPDATED_AVERAGE_EVALUATION_IN_STARS)
            .customAttributtesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return scheduledActivity;
    }

    @BeforeEach
    public void initTest() {
        scheduledActivity = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedScheduledActivity != null) {
            scheduledActivityRepository.delete(insertedScheduledActivity);
            insertedScheduledActivity = null;
        }
    }

    @Test
    @Transactional
    void createScheduledActivity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);
        var returnedScheduledActivityDTO = om.readValue(
            restScheduledActivityMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(scheduledActivityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ScheduledActivityDTO.class
        );

        // Validate the ScheduledActivity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedScheduledActivity = scheduledActivityMapper.toEntity(returnedScheduledActivityDTO);
        assertScheduledActivityUpdatableFieldsEquals(returnedScheduledActivity, getPersistedScheduledActivity(returnedScheduledActivity));

        insertedScheduledActivity = returnedScheduledActivity;
    }

    @Test
    @Transactional
    void createScheduledActivityWithExistingId() throws Exception {
        // Create the ScheduledActivity with an existing ID
        scheduledActivity.setId(1L);
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduledActivityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        scheduledActivity.setStartTime(null);

        // Create the ScheduledActivity, which fails.
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        restScheduledActivityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        scheduledActivity.setActivationStatus(null);

        // Create the ScheduledActivity, which fails.
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        restScheduledActivityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllScheduledActivities() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        // Get all the scheduledActivityList
        restScheduledActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduledActivity.getId().intValue())))
            .andExpect(jsonPath("$.[*].customizedName").value(hasItem(DEFAULT_CUSTOMIZED_NAME)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].numberOfAttendees").value(hasItem(DEFAULT_NUMBER_OF_ATTENDEES)))
            .andExpect(jsonPath("$.[*].averageEvaluationInStars").value(hasItem(DEFAULT_AVERAGE_EVALUATION_IN_STARS)))
            .andExpect(jsonPath("$.[*].customAttributtesDetailsJSON").value(hasItem(DEFAULT_CUSTOM_ATTRIBUTTES_DETAILS_JSON)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScheduledActivitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(scheduledActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScheduledActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(scheduledActivityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllScheduledActivitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(scheduledActivityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restScheduledActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(scheduledActivityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getScheduledActivity() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        // Get the scheduledActivity
        restScheduledActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, scheduledActivity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduledActivity.getId().intValue()))
            .andExpect(jsonPath("$.customizedName").value(DEFAULT_CUSTOMIZED_NAME))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.numberOfAttendees").value(DEFAULT_NUMBER_OF_ATTENDEES))
            .andExpect(jsonPath("$.averageEvaluationInStars").value(DEFAULT_AVERAGE_EVALUATION_IN_STARS))
            .andExpect(jsonPath("$.customAttributtesDetailsJSON").value(DEFAULT_CUSTOM_ATTRIBUTTES_DETAILS_JSON))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingScheduledActivity() throws Exception {
        // Get the scheduledActivity
        restScheduledActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingScheduledActivity() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheduledActivity
        ScheduledActivity updatedScheduledActivity = scheduledActivityRepository.findById(scheduledActivity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedScheduledActivity are not directly saved in db
        em.detach(updatedScheduledActivity);
        updatedScheduledActivity
            .customizedName(UPDATED_CUSTOMIZED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .numberOfAttendees(UPDATED_NUMBER_OF_ATTENDEES)
            .averageEvaluationInStars(UPDATED_AVERAGE_EVALUATION_IN_STARS)
            .customAttributtesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(updatedScheduledActivity);

        restScheduledActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduledActivityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isOk());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedScheduledActivityToMatchAllProperties(updatedScheduledActivity);
    }

    @Test
    @Transactional
    void putNonExistingScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, scheduledActivityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScheduledActivityWithPatch() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheduledActivity using partial update
        ScheduledActivity partialUpdatedScheduledActivity = new ScheduledActivity();
        partialUpdatedScheduledActivity.setId(scheduledActivity.getId());

        partialUpdatedScheduledActivity
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .averageEvaluationInStars(UPDATED_AVERAGE_EVALUATION_IN_STARS);

        restScheduledActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduledActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScheduledActivity))
            )
            .andExpect(status().isOk());

        // Validate the ScheduledActivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScheduledActivityUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedScheduledActivity, scheduledActivity),
            getPersistedScheduledActivity(scheduledActivity)
        );
    }

    @Test
    @Transactional
    void fullUpdateScheduledActivityWithPatch() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the scheduledActivity using partial update
        ScheduledActivity partialUpdatedScheduledActivity = new ScheduledActivity();
        partialUpdatedScheduledActivity.setId(scheduledActivity.getId());

        partialUpdatedScheduledActivity
            .customizedName(UPDATED_CUSTOMIZED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .numberOfAttendees(UPDATED_NUMBER_OF_ATTENDEES)
            .averageEvaluationInStars(UPDATED_AVERAGE_EVALUATION_IN_STARS)
            .customAttributtesDetailsJSON(UPDATED_CUSTOM_ATTRIBUTTES_DETAILS_JSON)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restScheduledActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedScheduledActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedScheduledActivity))
            )
            .andExpect(status().isOk());

        // Validate the ScheduledActivity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertScheduledActivityUpdatableFieldsEquals(
            partialUpdatedScheduledActivity,
            getPersistedScheduledActivity(partialUpdatedScheduledActivity)
        );
    }

    @Test
    @Transactional
    void patchNonExistingScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, scheduledActivityDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamScheduledActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        scheduledActivity.setId(longCount.incrementAndGet());

        // Create the ScheduledActivity
        ScheduledActivityDTO scheduledActivityDTO = scheduledActivityMapper.toDto(scheduledActivity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScheduledActivityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(scheduledActivityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ScheduledActivity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteScheduledActivity() throws Exception {
        // Initialize the database
        insertedScheduledActivity = scheduledActivityRepository.saveAndFlush(scheduledActivity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the scheduledActivity
        restScheduledActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, scheduledActivity.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return scheduledActivityRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ScheduledActivity getPersistedScheduledActivity(ScheduledActivity scheduledActivity) {
        return scheduledActivityRepository.findById(scheduledActivity.getId()).orElseThrow();
    }

    protected void assertPersistedScheduledActivityToMatchAllProperties(ScheduledActivity expectedScheduledActivity) {
        assertScheduledActivityAllPropertiesEquals(expectedScheduledActivity, getPersistedScheduledActivity(expectedScheduledActivity));
    }

    protected void assertPersistedScheduledActivityToMatchUpdatableProperties(ScheduledActivity expectedScheduledActivity) {
        assertScheduledActivityAllUpdatablePropertiesEquals(
            expectedScheduledActivity,
            getPersistedScheduledActivity(expectedScheduledActivity)
        );
    }
}
