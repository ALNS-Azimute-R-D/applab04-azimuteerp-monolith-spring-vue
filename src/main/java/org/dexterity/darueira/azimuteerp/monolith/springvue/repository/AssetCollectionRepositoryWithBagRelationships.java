package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.AssetCollection;
import org.springframework.data.domain.Page;

public interface AssetCollectionRepositoryWithBagRelationships {
    Optional<AssetCollection> fetchBagRelationships(Optional<AssetCollection> assetCollection);

    List<AssetCollection> fetchBagRelationships(List<AssetCollection> assetCollections);

    Page<AssetCollection> fetchBagRelationships(Page<AssetCollection> assetCollections);
}
