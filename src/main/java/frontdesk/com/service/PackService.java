package frontdesk.com.service;

import frontdesk.com.domain.Pack;
import frontdesk.com.repository.PackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Pack.
 */
@Service
@Transactional
public class PackService {

    private final Logger log = LoggerFactory.getLogger(PackService.class);

    private final PackRepository packRepository;

    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    /**
     * Save a pack.
     *
     * @param pack the entity to save
     * @return the persisted entity
     */
    public Pack save(Pack pack) {
        log.debug("Request to save Pack : {}", pack);
        return packRepository.save(pack);
    }

    /**
     * Get all the packs.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Pack> findAll() {
        log.debug("Request to get all Packs");
        return packRepository.findAll();
    }


    /**
     * Get one pack by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Pack> findOne(Long id) {
        log.debug("Request to get Pack : {}", id);
        return packRepository.findById(id);
    }

    /**
     * Delete the pack by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pack : {}", id);
        packRepository.deleteById(id);
    }
}
