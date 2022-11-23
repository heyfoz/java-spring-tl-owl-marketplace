package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.Personnel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PersonnelService {

	List<Personnel> getAllPersonnel();

	void savePersonnel(Personnel personnel);

	Personnel getPersonnelById(long id);

	void deletePersonnelById(long id);

	Page<Personnel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
