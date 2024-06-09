package org.dexterity.darueira.azimuteerp.monolith.springvue.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.PersonTestSamples.*;
import static org.dexterity.darueira.azimuteerp.monolith.springvue.domain.TypeOfPersonTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.dexterity.darueira.azimuteerp.monolith.springvue.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeOfPersonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOfPerson.class);
        TypeOfPerson typeOfPerson1 = getTypeOfPersonSample1();
        TypeOfPerson typeOfPerson2 = new TypeOfPerson();
        assertThat(typeOfPerson1).isNotEqualTo(typeOfPerson2);

        typeOfPerson2.setId(typeOfPerson1.getId());
        assertThat(typeOfPerson1).isEqualTo(typeOfPerson2);

        typeOfPerson2 = getTypeOfPersonSample2();
        assertThat(typeOfPerson1).isNotEqualTo(typeOfPerson2);
    }

    @Test
    void personsListTest() {
        TypeOfPerson typeOfPerson = getTypeOfPersonRandomSampleGenerator();
        Person personBack = getPersonRandomSampleGenerator();

        typeOfPerson.addPersonsList(personBack);
        assertThat(typeOfPerson.getPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getTypeOfPerson()).isEqualTo(typeOfPerson);

        typeOfPerson.removePersonsList(personBack);
        assertThat(typeOfPerson.getPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getTypeOfPerson()).isNull();

        typeOfPerson.personsLists(new HashSet<>(Set.of(personBack)));
        assertThat(typeOfPerson.getPersonsLists()).containsOnly(personBack);
        assertThat(personBack.getTypeOfPerson()).isEqualTo(typeOfPerson);

        typeOfPerson.setPersonsLists(new HashSet<>());
        assertThat(typeOfPerson.getPersonsLists()).doesNotContain(personBack);
        assertThat(personBack.getTypeOfPerson()).isNull();
    }
}
