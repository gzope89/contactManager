package com.dummy.contactmanager.service;

import java.util.List;

import java.util.Optional;

import com.dummy.contactmanager.entity.PersonDetails;

public interface PersonDetailsService {

	public PersonDetails getPersonDetailsBasedOnFirstNameAndLastName(PersonDetails personDetails);

	public PersonDetails savePersonDetails(PersonDetails personDetails);

	public PersonDetails updatePersonDetails(PersonDetails personDetails);

	Optional<PersonDetails> getOnePersonDetailsDetails(Long personDetailsId);

	List<PersonDetails> findAllPersonDetailss();

	void removePersonDetails(Long personDetailsId);

}
