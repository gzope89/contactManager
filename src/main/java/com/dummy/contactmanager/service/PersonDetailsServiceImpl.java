package com.dummy.contactmanager.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dummy.contactmanager.entity.PersonDetails;
import com.dummy.contactmanager.repo.PersonDetailsRepository;

@Service
public class PersonDetailsServiceImpl implements PersonDetailsService {

	@Autowired
	public  PersonDetailsRepository personDetailsRepository;

	@Override
	public PersonDetails getPersonDetailsBasedOnFirstNameAndLastName(PersonDetails personDetails) {
		return personDetailsRepository.findByFirstNameAndLastName(personDetails.getFirstName(),personDetails.getLastName());
	}

	@Override
	public PersonDetails savePersonDetails(PersonDetails personDetails) {
		return personDetailsRepository.save(personDetails);
	}

	@Override
	public PersonDetails updatePersonDetails(PersonDetails personDetails) {
		return personDetailsRepository.save(personDetails);
	}

	@Override
	public Optional<PersonDetails> getOnePersonDetailsDetails(Long personDetailsId) {
		return personDetailsRepository.findById(personDetailsId);
	}

	@Override
	public List<PersonDetails> findAllPersonDetailss() {
		return personDetailsRepository.findAll();
	}

	@Override
	public void removePersonDetails(Long personDetailsId) {
		personDetailsRepository.deleteById(personDetailsId);
	}

}
