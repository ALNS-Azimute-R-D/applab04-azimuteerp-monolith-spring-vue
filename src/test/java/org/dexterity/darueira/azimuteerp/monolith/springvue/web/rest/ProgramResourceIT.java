package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.ProgramAsserts.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil.createUpdateProxyForBean;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.dexterity.darueira.azimuteerp.monolith.springvue.IntegrationTest;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Program;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.enumeration.ActivationStatusEnum;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.ProgramRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.ProgramDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.ProgramMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProgramResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramResourceIT {

    private static final String DEFAULT_ACRONYM = "AAAAAAAAAA";
    private static final String UPDATED_ACRONYM = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FULL_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_PUBLIC = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_PUBLIC = "BBBBBBBBBB";

    private static final ActivationStatusEnum DEFAULT_ACTIVATION_STATUS = ActivationStatusEnum.INACTIVE;
    private static final ActivationStatusEnum UPDATED_ACTIVATION_STATUS = ActivationStatusEnum.ACTIVE;

    private static final String ENTITY_API_URL = "/api/programs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramMapper programMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramMockMvc;

    private Program program;

    private Program insertedProgram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createEntity(EntityManager em) {
        Program program = new Program()
            .acronym(DEFAULT_ACRONYM)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .fullDescription(DEFAULT_FULL_DESCRIPTION)
            .targetPublic(DEFAULT_TARGET_PUBLIC)
            .activationStatus(DEFAULT_ACTIVATION_STATUS);
        return program;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Program createUpdatedEntity(EntityManager em) {
        Program program = new Program()
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .targetPublic(UPDATED_TARGET_PUBLIC)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        return program;
    }

    @BeforeEach
    public void initTest() {
        program = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedProgram != null) {
            programRepository.delete(insertedProgram);
            insertedProgram = null;
        }
    }

    @Test
    @Transactional
    void createProgram() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);
        var returnedProgramDTO = om.readValue(
            restProgramMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProgramDTO.class
        );

        // Validate the Program in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProgram = programMapper.toEntity(returnedProgramDTO);
        assertProgramUpdatableFieldsEquals(returnedProgram, getPersistedProgram(returnedProgram));

        insertedProgram = returnedProgram;
    }

    @Test
    @Transactional
    void createProgramWithExistingId() throws Exception {
        // Create the Program with an existing ID
        program.setId(1L);
        ProgramDTO programDTO = programMapper.toDto(program);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        program.setName(null);

        // Create the Program, which fails.
        ProgramDTO programDTO = programMapper.toDto(program);

        restProgramMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivationStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        program.setActivationStatus(null);

        // Create the Program, which fails.
        ProgramDTO programDTO = programMapper.toDto(program);

        restProgramMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrograms() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        // Get all the programList
        restProgramMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(program.getId().intValue())))
            .andExpect(jsonPath("$.[*].acronym").value(hasItem(DEFAULT_ACRONYM)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fullDescription").value(hasItem(DEFAULT_FULL_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].targetPublic").value(hasItem(DEFAULT_TARGET_PUBLIC)))
            .andExpect(jsonPath("$.[*].activationStatus").value(hasItem(DEFAULT_ACTIVATION_STATUS.toString())));
    }

    @Test
    @Transactional
    void getProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        // Get the program
        restProgramMockMvc
            .perform(get(ENTITY_API_URL_ID, program.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(program.getId().intValue()))
            .andExpect(jsonPath("$.acronym").value(DEFAULT_ACRONYM))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fullDescription").value(DEFAULT_FULL_DESCRIPTION))
            .andExpect(jsonPath("$.targetPublic").value(DEFAULT_TARGET_PUBLIC))
            .andExpect(jsonPath("$.activationStatus").value(DEFAULT_ACTIVATION_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgram() throws Exception {
        // Get the program
        restProgramMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program
        Program updatedProgram = programRepository.findById(program.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgram are not directly saved in db
        em.detach(updatedProgram);
        updatedProgram
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .targetPublic(UPDATED_TARGET_PUBLIC)
            .activationStatus(UPDATED_ACTIVATION_STATUS);
        ProgramDTO programDTO = programMapper.toDto(updatedProgram);

        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgramToMatchAllProperties(updatedProgram);
    }

    @Test
    @Transactional
    void putNonExistingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program using partial update
        Program partialUpdatedProgram = new Program();
        partialUpdatedProgram.setId(program.getId());

        partialUpdatedProgram
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .targetPublic(UPDATED_TARGET_PUBLIC)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgram.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProgram, program), getPersistedProgram(program));
    }

    @Test
    @Transactional
    void fullUpdateProgramWithPatch() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the program using partial update
        Program partialUpdatedProgram = new Program();
        partialUpdatedProgram.setId(program.getId());

        partialUpdatedProgram
            .acronym(UPDATED_ACRONYM)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fullDescription(UPDATED_FULL_DESCRIPTION)
            .targetPublic(UPDATED_TARGET_PUBLIC)
            .activationStatus(UPDATED_ACTIVATION_STATUS);

        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgram.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgram))
            )
            .andExpect(status().isOk());

        // Validate the Program in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramUpdatableFieldsEquals(partialUpdatedProgram, getPersistedProgram(partialUpdatedProgram));
    }

    @Test
    @Transactional
    void patchNonExistingProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgram() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        program.setId(longCount.incrementAndGet());

        // Create the Program
        ProgramDTO programDTO = programMapper.toDto(program);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Program in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgram() throws Exception {
        // Initialize the database
        insertedProgram = programRepository.saveAndFlush(program);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the program
        restProgramMockMvc
            .perform(delete(ENTITY_API_URL_ID, program.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programRepository.count();
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

    protected Program getPersistedProgram(Program program) {
        return programRepository.findById(program.getId()).orElseThrow();
    }

    protected void assertPersistedProgramToMatchAllProperties(Program expectedProgram) {
        assertProgramAllPropertiesEquals(expectedProgram, getPersistedProgram(expectedProgram));
    }

    protected void assertPersistedProgramToMatchUpdatableProperties(Program expectedProgram) {
        assertProgramAllUpdatablePropertiesEquals(expectedProgram, getPersistedProgram(expectedProgram));
    }
}
