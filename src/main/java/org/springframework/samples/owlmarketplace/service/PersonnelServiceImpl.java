package org.springframework.samples.owlmarketplace.service;

import org.springframework.samples.owlmarketplace.model.Personnel;
import org.springframework.samples.owlmarketplace.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonnelServiceImpl implements PersonnelService {

	@Autowired
	private PersonnelRepository personnelRepository;

	@Override
	public List<Personnel> getAllPersonnel() {
		return personnelRepository.findAll();
	}

	@Override
	public void savePersonnel(Personnel personnel) {
		this.personnelRepository.save(personnel);
	}

	@Override
	public Personnel getPersonnelById(long id) {
		Optional<Personnel> optional = personnelRepository.findById(id);
		Personnel personnel = null;
		if (optional.isPresent()) {
			personnel = optional.get();
		}
		else {
			throw new RuntimeException("Personnel not found for id: " + id);
		}
		return personnel;
	}

	@Override
	public void deletePersonnelById(long id) {
		this.personnelRepository.deleteById(id);
	}

	@Override
	public Page<Personnel> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.personnelRepository.findAll(pageable);
	}

}
