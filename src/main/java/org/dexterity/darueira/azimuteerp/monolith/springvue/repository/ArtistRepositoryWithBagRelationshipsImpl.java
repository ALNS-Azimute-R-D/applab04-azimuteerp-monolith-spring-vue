package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ArtistRepositoryWithBagRelationshipsImpl implements ArtistRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ARTISTS_PARAMETER = "artists";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Artist> fetchBagRelationships(Optional<Artist> artist) {
        return artist.map(this::fetchArtists);
    }

    @Override
    public Page<Artist> fetchBagRelationships(Page<Artist> artists) {
        return new PageImpl<>(fetchBagRelationships(artists.getContent()), artists.getPageable(), artists.getTotalElements());
    }

    @Override
    public List<Artist> fetchBagRelationships(List<Artist> artists) {
        return Optional.of(artists).map(this::fetchArtists).orElse(Collections.emptyList());
    }

    Artist fetchArtists(Artist result) {
        return entityManager
            .createQuery("select artist from Artist artist left join fetch artist.artists where artist.id = :id", Artist.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Artist> fetchArtists(List<Artist> artists) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, artists.size()).forEach(index -> order.put(artists.get(index).getId(), index));
        List<Artist> result = entityManager
            .createQuery("select artist from Artist artist left join fetch artist.artists where artist in :artists", Artist.class)
            .setParameter(ARTISTS_PARAMETER, artists)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
