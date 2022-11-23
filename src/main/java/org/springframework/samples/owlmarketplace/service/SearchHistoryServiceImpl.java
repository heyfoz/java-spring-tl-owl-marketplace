package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.SearchObject;
import org.springframework.samples.owlmarketplace.repository.LovedListingRepo;
import org.springframework.samples.owlmarketplace.repository.SearchHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

	@Autowired
	private LovedListingRepo lovedListingRepo;

	@Autowired
	private SearchHistoryRepo searchHistoryRepo;

	@Override
	public List<SearchObject> getAllSearchHistory() {
		return searchHistoryRepo.findAll();
	}

	@Override
	public SearchObject getSearchById(long id) {
		Optional<SearchObject> optional = searchHistoryRepo.findById(id);
		SearchObject searchObject = null;
		if (optional.isPresent()) {
			searchObject = optional.get();
		}
		else {
			throw new RuntimeException("Loved listing not found for id: " + id);
		}
		return searchObject;
	}

	@Override
	public void saveSearch(SearchObject searchObject) {
		this.searchHistoryRepo.save(searchObject);
	}

	@Override
	public void saveSearchHistory2(SearchObject searchObject) {
		this.searchHistoryRepo.save(searchObject);
	}

	@Override
	public void deleteSearchById(long id) {
		this.lovedListingRepo.deleteById(id);
	}

	// @Override
	// public Page<Loan> findPaginated(int pageNo, int pageSize, String sortField, String
	// sortDirection) {
	// Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
	// Sort.by(sortField).ascending() :
	// Sort.by(sortField).descending();
	// Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
	// return this.lovedListingRepo.findAll(pageable);
	// }

}
