package org.springframework.samples.owlmarketplace.repository;

import org.springframework.samples.owlmarketplace.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Used in the creation of repositories

@Repository
public interface LovedListingRepo extends JpaRepository<Listing, Long> {
	 @Override Optional<Listing> findById(Long id);
}
