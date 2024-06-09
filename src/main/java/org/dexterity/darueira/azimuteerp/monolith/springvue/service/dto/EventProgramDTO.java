package org.dexterity.darueira.azimuteerp.monolith.springvue.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.dexterity.darueira.azimuteerp.monolith.springvue.domain.EventProgram} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventProgramDTO implements Serializable {

    private Long id;

    private Double percentageExecution;

    private EventDTO event;

    private ProgramDTO program;

    private PersonDTO responsiblePerson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentageExecution() {
        return percentageExecution;
    }

    public void setPercentageExecution(Double percentageExecution) {
        this.percentageExecution = percentageExecution;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }

    public PersonDTO getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(PersonDTO responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventProgramDTO)) {
            return false;
        }

        EventProgramDTO eventProgramDTO = (EventProgramDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventProgramDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventProgramDTO{" +
            "id=" + getId() +
            ", percentageExecution=" + getPercentageExecution() +
            ", event=" + getEvent() +
            ", program=" + getProgram() +
            ", responsiblePerson=" + getResponsiblePerson() +
            "}";
    }
}
