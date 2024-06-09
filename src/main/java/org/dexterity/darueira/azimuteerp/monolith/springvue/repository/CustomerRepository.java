package org.dexterity.darueira.azimuteerp.monolith.springvue.repository;

import java.util.List;
import java.util.Optional;
import org.dexterity.darueira.azimuteerp.monolith.springvue.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    default Optional<Customer> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Customer> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Customer> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select customer from Customer customer left join fetch customer.buyerPerson left join fetch customer.customerType left join fetch customer.district",
        countQuery = "select count(customer) from Customer customer"
    )
    Page<Customer> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select customer from Customer customer left join fetch customer.buyerPerson left join fetch customer.customerType left join fetch customer.district"
    )
    List<Customer> findAllWithToOneRelationships();

    @Query(
        "select customer from Customer customer left join fetch customer.buyerPerson left join fetch customer.customerType left join fetch customer.district where customer.id =:id"
    )
    Optional<Customer> findOneWithToOneRelationships(@Param("id") Long id);
}
