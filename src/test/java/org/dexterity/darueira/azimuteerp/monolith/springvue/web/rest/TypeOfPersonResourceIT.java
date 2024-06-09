package org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPersonAsserts.*;
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
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPerson;
import org.dexterity.darueira.azimuteerp.monolith.springvue.repository.TypeOfPersonRepository;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto.TypeOfPersonDTO;
import org.dexterity.darueira.azimuteerp.monolith.springvue.service.mapper.TypeOfPersonMapper;
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
 * Integration tests for the {@link TypeOfPersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeOfPersonResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/type-of-people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TypeOfPersonRepository typeOfPersonRepository;

    @Autowired
    private TypeOfPersonMapper typeOfPersonMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeOfPersonMockMvc;

    private TypeOfPerson typeOfPerson;

    private TypeOfPerson insertedTypeOfPerson;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfPerson createEntity(EntityManager em) {
        TypeOfPerson typeOfPerson = new TypeOfPerson().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return typeOfPerson;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeOfPerson createUpdatedEntity(EntityManager em) {
        TypeOfPerson typeOfPerson = new TypeOfPerson().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return typeOfPerson;
    }

    @BeforeEach
    public void initTest() {
        typeOfPerson = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTypeOfPerson != null) {
            typeOfPersonRepository.delete(insertedTypeOfPerson);
            insertedTypeOfPerson = null;
        }
    }

    @Test
    @Transactional
    void createTypeOfPerson() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);
        var returnedTypeOfPersonDTO = om.readValue(
            restTypeOfPersonMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfPersonDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TypeOfPersonDTO.class
        );

        // Validate the TypeOfPerson in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTypeOfPerson = typeOfPersonMapper.toEntity(returnedTypeOfPersonDTO);
        assertTypeOfPersonUpdatableFieldsEquals(returnedTypeOfPerson, getPersistedTypeOfPerson(returnedTypeOfPerson));

        insertedTypeOfPerson = returnedTypeOfPerson;
    }

    @Test
    @Transactional
    void createTypeOfPersonWithExistingId() throws Exception {
        // Create the TypeOfPerson with an existing ID
        typeOfPerson.setId(1L);
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeOfPersonMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfPerson.setCode(null);

        // Create the TypeOfPerson, which fails.
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        restTypeOfPersonMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        typeOfPerson.setDescription(null);

        // Create the TypeOfPerson, which fails.
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        restTypeOfPersonMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTypeOfPeople() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        // Get all the typeOfPersonList
        restTypeOfPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeOfPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTypeOfPerson() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        // Get the typeOfPerson
        restTypeOfPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, typeOfPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeOfPerson.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTypeOfPerson() throws Exception {
        // Get the typeOfPerson
        restTypeOfPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTypeOfPerson() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfPerson
        TypeOfPerson updatedTypeOfPerson = typeOfPersonRepository.findById(typeOfPerson.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTypeOfPerson are not directly saved in db
        em.detach(updatedTypeOfPerson);
        updatedTypeOfPerson.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(updatedTypeOfPerson);

        restTypeOfPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfPersonDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTypeOfPersonToMatchAllProperties(updatedTypeOfPerson);
    }

    @Test
    @Transactional
    void putNonExistingTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeOfPersonDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeOfPersonWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfPerson using partial update
        TypeOfPerson partialUpdatedTypeOfPerson = new TypeOfPerson();
        partialUpdatedTypeOfPerson.setId(typeOfPerson.getId());

        partialUpdatedTypeOfPerson.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTypeOfPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfPerson))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfPersonUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTypeOfPerson, typeOfPerson),
            getPersistedTypeOfPerson(typeOfPerson)
        );
    }

    @Test
    @Transactional
    void fullUpdateTypeOfPersonWithPatch() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the typeOfPerson using partial update
        TypeOfPerson partialUpdatedTypeOfPerson = new TypeOfPerson();
        partialUpdatedTypeOfPerson.setId(typeOfPerson.getId());

        partialUpdatedTypeOfPerson.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTypeOfPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeOfPerson.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTypeOfPerson))
            )
            .andExpect(status().isOk());

        // Validate the TypeOfPerson in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTypeOfPersonUpdatableFieldsEquals(partialUpdatedTypeOfPerson, getPersistedTypeOfPerson(partialUpdatedTypeOfPerson));
    }

    @Test
    @Transactional
    void patchNonExistingTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeOfPersonDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeOfPerson() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        typeOfPerson.setId(longCount.incrementAndGet());

        // Create the TypeOfPerson
        TypeOfPersonDTO typeOfPersonDTO = typeOfPersonMapper.toDto(typeOfPerson);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeOfPersonMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(typeOfPersonDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeOfPerson in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeOfPerson() throws Exception {
        // Initialize the database
        insertedTypeOfPerson = typeOfPersonRepository.saveAndFlush(typeOfPerson);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the typeOfPerson
        restTypeOfPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeOfPerson.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return typeOfPersonRepository.count();
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

    protected TypeOfPerson getPersistedTypeOfPerson(TypeOfPerson typeOfPerson) {
        return typeOfPersonRepository.findById(typeOfPerson.getId()).orElseThrow();
    }

    protected void assertPersistedTypeOfPersonToMatchAllProperties(TypeOfPerson expectedTypeOfPerson) {
        assertTypeOfPersonAllPropertiesEquals(expectedTypeOfPerson, getPersistedTypeOfPerson(expectedTypeOfPerson));
    }

    protected void assertPersistedTypeOfPersonToMatchUpdatableProperties(TypeOfPerson expectedTypeOfPerson) {
        assertTypeOfPersonAllUpdatablePropertiesEquals(expectedTypeOfPerson, getPersistedTypeOfPerson(expectedTypeOfPerson));
    }
}
