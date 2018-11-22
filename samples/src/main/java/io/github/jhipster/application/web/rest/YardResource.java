package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Yard;
import io.github.jhipster.application.repository.YardRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Yard.
 */
@RestController
@RequestMapping("/api")
public class YardResource {

    private final Logger log = LoggerFactory.getLogger(YardResource.class);

    private static final String ENTITY_NAME = "yard";

    private final YardRepository yardRepository;

    public YardResource(YardRepository yardRepository) {
        this.yardRepository = yardRepository;
    }

    /**
     * POST  /yards : Create a new yard.
     *
     * @param yard the yard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new yard, or with status 400 (Bad Request) if the yard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/yards")
    @Timed
    public ResponseEntity<Yard> createYard(@RequestBody Yard yard) throws URISyntaxException {
        log.debug("REST request to save Yard : {}", yard);
        if (yard.getId() != null) {
            throw new BadRequestAlertException("A new yard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Yard result = yardRepository.save(yard);
        return ResponseEntity.created(new URI("/api/yards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yards : Updates an existing yard.
     *
     * @param yard the yard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated yard,
     * or with status 400 (Bad Request) if the yard is not valid,
     * or with status 500 (Internal Server Error) if the yard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/yards")
    @Timed
    public ResponseEntity<Yard> updateYard(@RequestBody Yard yard) throws URISyntaxException {
        log.debug("REST request to update Yard : {}", yard);
        if (yard.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Yard result = yardRepository.save(yard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, yard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yards : get all the yards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of yards in body
     */
    @GetMapping("/yards")
    @Timed
    public List<Yard> getAllYards() {
        log.debug("REST request to get all Yards");
        return yardRepository.findAll();
    }

    /**
     * GET  /yards/:id : get the "id" yard.
     *
     * @param id the id of the yard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the yard, or with status 404 (Not Found)
     */
    @GetMapping("/yards/{id}")
    @Timed
    public ResponseEntity<Yard> getYard(@PathVariable Long id) {
        log.debug("REST request to get Yard : {}", id);
        Optional<Yard> yard = yardRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(yard);
    }

    /**
     * DELETE  /yards/:id : delete the "id" yard.
     *
     * @param id the id of the yard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/yards/{id}")
    @Timed
    public ResponseEntity<Void> deleteYard(@PathVariable Long id) {
        log.debug("REST request to delete Yard : {}", id);

        yardRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
