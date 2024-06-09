package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Event;
import org.springframework.data.domain.Page;

public interface EventRepositoryWithBagRelationships {
    Optional<Event> fetchBagRelationships(Optional<Event> event);

    List<Event> fetchBagRelationships(List<Event> events);

    Page<Event> fetchBagRelationships(Page<Event> events);
}
