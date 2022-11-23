package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.Listing;
import org.springframework.samples.owlmarketplace.repository.LovedListingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LovedListingServiceImpl implements LovedListingService {

	@Autowired
	private LovedListingRepo lovedListingRepo;

	@Override
	public List<Listing> getAllListings() {
		return lovedListingRepo.findAll();
	}

	@Override
	public void saveListing(Listing listing) {
		this.lovedListingRepo.save(listing);
	}

	@Override
	public Listing getListingById(long id) {
		Optional<Listing> optional = lovedListingRepo.findById(id);
		Listing listing = null;
		if (optional.isPresent()) {
			listing = optional.get();
		}
		else {
			throw new RuntimeException("Loan not found for id: " + id);
		}
		return listing;
	}

	@Override
	public void deleteListingById(long id) {
		this.lovedListingRepo.deleteById(id);
	}

	@Override
	public Page<Listing> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.lovedListingRepo.findAll(pageable);
	}
}
