package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.SearchObject;

import java.util.List;

public interface SearchHistoryService {

	List<SearchObject> getAllSearchHistory();

	void saveSearch(SearchObject searchObject);

	void saveSearchHistory2(SearchObject searchObject);

	SearchObject getSearchById(long id);

	void deleteSearchById(long id);
	// Page<SearchObject> findPaginated(int pageNo, int pageSize, String sortField, String
	// sortDirection);

}
