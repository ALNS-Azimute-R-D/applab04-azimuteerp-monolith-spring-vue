package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AssetCollectionRepositoryWithBagRelationshipsImpl implements AssetCollectionRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ASSETCOLLECTIONS_PARAMETER = "assetCollections";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AssetCollection> fetchBagRelationships(Optional<AssetCollection> assetCollection) {
        return assetCollection.map(this::fetchAssets);
    }

    @Override
    public Page<AssetCollection> fetchBagRelationships(Page<AssetCollection> assetCollections) {
        return new PageImpl<>(
            fetchBagRelationships(assetCollections.getContent()),
            assetCollections.getPageable(),
            assetCollections.getTotalElements()
        );
    }

    @Override
    public List<AssetCollection> fetchBagRelationships(List<AssetCollection> assetCollections) {
        return Optional.of(assetCollections).map(this::fetchAssets).orElse(Collections.emptyList());
    }

    AssetCollection fetchAssets(AssetCollection result) {
        return entityManager
            .createQuery(
                "select assetCollection from AssetCollection assetCollection left join fetch assetCollection.assets where assetCollection.id = :id",
                AssetCollection.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<AssetCollection> fetchAssets(List<AssetCollection> assetCollections) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, assetCollections.size()).forEach(index -> order.put(assetCollections.get(index).getId(), index));
        List<AssetCollection> result = entityManager
            .createQuery(
                "select assetCollection from AssetCollection assetCollection left join fetch assetCollection.assets where assetCollection in :assetCollections",
                AssetCollection.class
            )
            .setParameter(ASSETCOLLECTIONS_PARAMETER, assetCollections)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
