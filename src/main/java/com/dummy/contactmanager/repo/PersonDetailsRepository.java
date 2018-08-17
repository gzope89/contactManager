package com.dummy.contactmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.contactmanager.entity.PersonDetails;
@Repository
public interface PersonDetailsRepository extends JpaRepository<PersonDetails, Long>{

	PersonDetails findByFirstNameAndLastName(String first_name, String last_name);

}
