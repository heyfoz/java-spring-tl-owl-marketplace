package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.Listing;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LovedListingService {

	List<Listing> getAllListings();

	void saveListing(Listing listing);

	Listing getListingById(long id);

	void deleteListingById(long id);

	Page<Listing> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
