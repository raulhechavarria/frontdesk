package frontdesk.com.web.rest;

import com.codahale.metrics.annotation.Timed;
import frontdesk.com.domain.Pack;
import frontdesk.com.service.PackService;
import frontdesk.com.web.rest.errors.BadRequestAlertException;
import frontdesk.com.web.rest.util.HeaderUtil;
import frontdesk.com.service.dto.PackCriteria;
import frontdesk.com.service.PackQueryService;
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
 * REST controller for managing Pack.
 */
@RestController
@RequestMapping("/api")
public class PackResource {

    private final Logger log = LoggerFactory.getLogger(PackResource.class);

    private static final String ENTITY_NAME = "pack";

    private final PackService packService;

    private final PackQueryService packQueryService;

    public PackResource(PackService packService, PackQueryService packQueryService) {
        this.packService = packService;
        this.packQueryService = packQueryService;
    }

    /**
     * POST  /packs : Create a new pack.
     *
     * @param pack the pack to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pack, or with status 400 (Bad Request) if the pack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/packs")
    @Timed
    public ResponseEntity<Pack> createPack(@RequestBody Pack pack) throws URISyntaxException {
        log.debug("REST request to save Pack : {}", pack);
        if (pack.getId() != null) {
            throw new BadRequestAlertException("A new pack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pack result = packService.save(pack);
        return ResponseEntity.created(new URI("/api/packs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /packs : Updates an existing pack.
     *
     * @param pack the pack to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pack,
     * or with status 400 (Bad Request) if the pack is not valid,
     * or with status 500 (Internal Server Error) if the pack couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/packs")
    @Timed
    public ResponseEntity<Pack> updatePack(@RequestBody Pack pack) throws URISyntaxException {
        log.debug("REST request to update Pack : {}", pack);
        if (pack.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pack result = packService.save(pack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pack.getId().toString()))
            .body(result);
    }

    /**
     * GET  /packs : get all the packs.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of packs in body
     */
    @GetMapping("/packs")
    @Timed
    public ResponseEntity<List<Pack>> getAllPacks(PackCriteria criteria) {
        log.debug("REST request to get Packs by criteria: {}", criteria);
        List<Pack> entityList = packQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /packs/count : count all the packs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/packs/count")
    @Timed
    public ResponseEntity<Long> countPacks(PackCriteria criteria) {
        log.debug("REST request to count Packs by criteria: {}", criteria);
        return ResponseEntity.ok().body(packQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /packs/:id : get the "id" pack.
     *
     * @param id the id of the pack to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pack, or with status 404 (Not Found)
     */
    @GetMapping("/packs/{id}")
    @Timed
    public ResponseEntity<Pack> getPack(@PathVariable Long id) {
        log.debug("REST request to get Pack : {}", id);
        Optional<Pack> pack = packService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pack);
    }

    /**
     * DELETE  /packs/:id : delete the "id" pack.
     *
     * @param id the id of the pack to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/packs/{id}")
    @Timed
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        log.debug("REST request to delete Pack : {}", id);
        packService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
