package frontdesk.com.repository;

import frontdesk.com.domain.Pack;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackRepository extends JpaRepository<Pack, Long>, JpaSpecificationExecutor<Pack> {

}
