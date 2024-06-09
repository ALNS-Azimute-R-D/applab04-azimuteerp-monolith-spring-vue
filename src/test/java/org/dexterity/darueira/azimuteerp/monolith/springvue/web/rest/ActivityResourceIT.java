package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ActivityAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Activity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfActivity;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ActivityRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.ActivityService;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ActivityDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ActivityMapper;
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
 * Integration tests for the {@link ActivityResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ActivityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FULL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_GOALS = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_GOALS = "BBBBBBBBBB";

    private static final Duration DEFAULT_ESTIMATED_DURATION_TIME = Duration.ofHours(6);
    private static final Duration UPDATED_ESTIMATED_DURATION_TIME = Duration.ofHours(12);

    private static final LocalDate DEFAULT_LAST_PERFORMED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_PERFORMED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/activities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActivityRepository activityRepository;

    @Mock
    private ActivityRepository activityRepositoryMock;

    @Autowired
    private ActivityMapper activityMapper;

    @Mock
    private ActivityService activityServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivityMockMvc;

    private Activity activity;

    private Activity insertedActivity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createEntity(EntityManager em) {
        Activity activity = new Activity()
            .name(DEFAULT_NAME)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .fullDescription(DEFAULT_FULL_DESCRIPTION)
            .mainGoals(DEFAULT_MAIN_GOALS)
            .estimatedDurationTime(DEFAULT_ESTIMATED_DURATION_TIME)
            .lastPerformedDate(DEFAULT_LAST_PERFORMED_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        // Add required entity
        TypeOfActivity typeOfActivity;
        if (TestUtil.findAll(em, TypeOfActivity.class).isEmpty()) {
            typeOfActivity = TypeOfActivityResourceIT.createEntity(em);
            em.persist(typeOfActivity);
            em.flush();
        } else {
            typeOfActivity = TestUtil.findAll(em, TypeOfActivity.class).get(0);
        }
        activity.setTypeOfActivity(typeOfActivity);
        return activity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activity createUpdatedEntity(EntityManager em) {
        Activity activity = new Activity()
            .name(UPDATED_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .mainGoals(UPDATED_MAIN_GOALS)
            .estimatedDurationTime(UPDATED_ESTIMATED_DURATION_TIME)
            .lastPerformedDate(UPDATED_LAST_PERFORMED_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        // Add required entity
        TypeOfActivity typeOfActivity;
        if (TestUtil.findAll(em, TypeOfActivity.class).isEmpty()) {
            typeOfActivity = TypeOfActivityResourceIT.createUpdatedEntity(em);
            em.persist(typeOfActivity);
            em.flush();
        } else {
            typeOfActivity = TestUtil.findAll(em, TypeOfActivity.class).get(0);
        }
        activity.setTypeOfActivity(typeOfActivity);
        return activity;
    }

    @BeforeEach
    public void initTest() {
        activity = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedActivity != null) {
            activityRepository.delete(insertedActivity);
            insertedActivity = null;
        }
    }

    @Test
    @Transactional
    void createActivity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);
        var returnedActivityDTO = om.readValue(
            restActivityMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activityDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ActivityDTO.class
        );

        // Validate the Activity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedActivity = activityMapper.toEntity(returnedActivityDTO);
        assertActivityUpdatableFieldsEquals(returnedActivity, getPersistedActivity(returnedActivity));

        insertedActivity = returnedActivity;
    }

    @Test
    @Transactional
    void createActivityWithExistingId() throws Exception {
        // Create the Activity with an existing ID
        activity.setId(1L);
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activity.setName(null);

        // Create the Activity, which fails.
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        restActivityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activity.setActivationStatus(null);

        // Create the Activity, which fails.
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        restActivityMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activityDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActivities() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        // Get all the activityList
        restActivityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fullDescription").value(hasItem(DEFAULT_FULL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].mainGoals").value(hasItem(DEFAULT_MAIN_GOALS)))
            .andExpect(jsonPath("$.[*].estimatedDurationTime").value(hasItem(DEFAULT_ESTIMATED_DURATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].lastPerformedDate").value(hasItem(DEFAULT_LAST_PERFORMED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(activityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(activityServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(activityServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivityMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(activityRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getActivity() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        // Get the activity
        restActivityMockMvc
            .perform(get(ENTITY_API_URL_ID, activity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION))
            .andExpect(jsonPath("$.fullDescription").value(DEFAULT_FULL_DESCRIPTION))
            .andExpect(jsonPath("$.mainGoals").value(DEFAULT_MAIN_GOALS))
            .andExpect(jsonPath("$.estimatedDurationTime").value(DEFAULT_ESTIMATED_DURATION_TIME.toString()))
            .andExpect(jsonPath("$.lastPerformedDate").value(DEFAULT_LAST_PERFORMED_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActivity() throws Exception {
        // Get the activity
        restActivityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivity() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activity
        Activity updatedActivity = activityRepository.findById(activity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActivity are not directly saved in db
        em.detach(updatedActivity);
        updatedActivity
            .name(UPDATED_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .mainGoals(UPDATED_MAIN_GOALS)
            .estimatedDurationTime(UPDATED_ESTIMATED_DURATION_TIME)
            .lastPerformedDate(UPDATED_LAST_PERFORMED_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        ActivityDTO activityDTO = activityMapper.toDto(updatedActivity);

        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActivityToMatchAllProperties(updatedActivity);
    }

    @Test
    @Transactional
    void putNonExistingActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activityDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .name(UPDATED_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .lastPerformedDate(UPDATED_LAST_PERFORMED_DATE);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivityUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedActivity, activity), getPersistedActivity(activity));
    }

    @Test
    @Transactional
    void fullUpdateActivityWithPatch() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activity using partial update
        Activity partialUpdatedActivity = new Activity();
        partialUpdatedActivity.setId(activity.getId());

        partialUpdatedActivity
            .name(UPDATED_NAME)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .mainGoals(UPDATED_MAIN_GOALS)
            .estimatedDurationTime(UPDATED_ESTIMATED_DURATION_TIME)
            .lastPerformedDate(UPDATED_LAST_PERFORMED_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivity.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivity))
            )
            .andExpect(status().isOk());

        // Validate the Activity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivityUpdatableFieldsEquals(partialUpdatedActivity, getPersistedActivity(partialUpdatedActivity));
    }

    @Test
    @Transactional
    void patchNonExistingActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activityDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activity.setId(longCount.incrementAndGet());

        // Create the Activity
        ActivityDTO activityDTO = activityMapper.toDto(activity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivityMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Activity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivity() throws Exception {
        // Initialize the database
        insertedActivity = activityRepository.saveAndFlush(activity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activity
        restActivityMockMvc
            .perform(delete(ENTITY_API_URL_ID, activity.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activityRepository.count();
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

    protected Activity getPersistedActivity(Activity activity) {
        return activityRepository.findById(activity.getId()).orElseThrow();
    }

    protected void assertPersistedActivityToMatchAllProperties(Activity expectedActivity) {
        assertActivityAllPropertiesEquals(expectedActivity, getPersistedActivity(expectedActivity));
    }

    protected void assertPersistedActivityToMatchUpdatableProperties(Activity expectedActivity) {
        assertActivityAllUpdatablePropertiesEquals(expectedActivity, getPersistedActivity(expectedActivity));
    }
}
