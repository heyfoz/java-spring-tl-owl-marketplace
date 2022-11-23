package org.springframework.samples.owlmarketplace.repository;

import org.springframework.samples.owlmarketplace.model.Personnel; // Import personnel model class
// PersonnelRepository class to extend JpaRepository
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Used in the creation of repositories
import java.util.Optional;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {

	@Override
	Optional<Personnel> findById(Long id);

}
