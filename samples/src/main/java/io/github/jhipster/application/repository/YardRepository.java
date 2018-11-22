package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Yard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Yard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YardRepository extends JpaRepository<Yard, Long> {

}
