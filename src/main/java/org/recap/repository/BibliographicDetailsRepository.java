package org.recap.repository;

import org.recap.model.BibliographicEntity;
import org.recap.model.BibliographicPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pvsubrah on 6/10/16.
 */
public interface BibliographicDetailsRepository extends JpaRepository<BibliographicEntity, BibliographicPK> {

}
