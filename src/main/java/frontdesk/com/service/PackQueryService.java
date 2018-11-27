package frontdesk.com.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import frontdesk.com.domain.Pack;
import frontdesk.com.domain.*; // for static metamodels
import frontdesk.com.repository.PackRepository;
import frontdesk.com.service.dto.PackCriteria;

/**
 * Service for executing complex queries for Pack entities in the database.
 * The main input is a {@link PackCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pack} or a {@link Page} of {@link Pack} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PackQueryService extends QueryService<Pack> {

    private final Logger log = LoggerFactory.getLogger(PackQueryService.class);

    private final PackRepository packRepository;

    public PackQueryService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    /**
     * Return a {@link List} of {@link Pack} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pack> findByCriteria(PackCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pack> specification = createSpecification(criteria);
        return packRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Pack} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pack> findByCriteria(PackCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pack> specification = createSpecification(criteria);
        return packRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PackCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pack> specification = createSpecification(criteria);
        return packRepository.count(specification);
    }

    /**
     * Function to convert PackCriteria to a {@link Specification}
     */
    private Specification<Pack> createSpecification(PackCriteria criteria) {
        Specification<Pack> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Pack_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Pack_.name));
            }
            if (criteria.getNameFrontDeskReceive() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameFrontDeskReceive(), Pack_.nameFrontDeskReceive));
            }
            if (criteria.getNameFrontDeskDelivery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameFrontDeskDelivery(), Pack_.nameFrontDeskDelivery));
            }
            if (criteria.getNamePickup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNamePickup(), Pack_.namePickup));
            }
            if (criteria.getDateReceived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReceived(), Pack_.dateReceived));
            }
            if (criteria.getDatePickup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePickup(), Pack_.datePickup));
            }
        }
        return specification;
    }
}
