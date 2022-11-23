package org.springframework.samples.owlmarketplace.repository;

import org.springframework.samples.owlmarketplace.model.SearchObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Used in the creation of repositories

@Repository
public interface SearchHistoryRepo extends JpaRepository<SearchObject, Long> {

	/*
	 * @Override Optional<LoanApp> findById(Long id);
	 */

}
