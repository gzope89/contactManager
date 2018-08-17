package com.dummy.contactmanager.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.contactmanager.entity.PersonDetails;
import com.dummy.contactmanager.service.PersonDetailsService;

@RestController
@RequestMapping(path="/api/personDetails")
public class PersonDetailsController {

	@Autowired
	public PersonDetailsService personDetailsService;

	@GetMapping("/")
	public String demo(){
		return "Hello there!!!";
	}

	public static String emptyString = "";
	public static int zeroVal = 0;

	/**
	 * 
	 * This method checks whether incoming parameter are unique and first name and last name is mandatory for
	 * creating new person  details.
	 * 
	 * @param PersonDetails
	 * @return PersonDetails as response entity sent over HTTP in Json format
	 */
	@PostMapping(
			path = "/save",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<PersonDetails> save(@RequestBody PersonDetails PersonDetails){

		if(PersonDetails.getFirstName() != null && PersonDetails.getFirstName() != emptyString
				&& PersonDetails.getLastName() != null && PersonDetails.getLastName() != emptyString){
			if(personDetailsService.getPersonDetailsBasedOnFirstNameAndLastName(PersonDetails) == null){
				return new ResponseEntity<PersonDetails>(personDetailsService.savePersonDetails(PersonDetails), HttpStatus.CREATED);
			}else{
				return new ResponseEntity<PersonDetails>(HttpStatus.CONFLICT);
			}
		}else{
			return new ResponseEntity<PersonDetails>(new PersonDetails(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * 
	 * This method checks whether incoming request have valid first name , last name & person id.
	 *  If yes then update person record else send bad request error.
	 *  
	 * @param PersonDetails
	 * @return  PersonDetails as response entity sent over HTTP in Json format
	 */
	@RequestMapping(
			path = "/update",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDetails> update(
			@RequestBody PersonDetails PersonDetails){
		if((PersonDetails.getFirstName() != null && PersonDetails.getFirstName() != emptyString 
				&& PersonDetails.getLastName() != null && PersonDetails.getLastName() != emptyString)
				|| (PersonDetails.getPersonId()!= null && PersonDetails.getPersonId() != zeroVal)){
			return new ResponseEntity<PersonDetails>(personDetailsService.updatePersonDetails(PersonDetails), HttpStatus.OK);
		}else{
			return new ResponseEntity<PersonDetails>( HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 
	 * This method accepts person id as URL path variable. Method checks for positive value for positive value records are fetched from DB,
	 * else bad request error thrown to user
	 * 
	 * @param personDetailsId
	 * @return PersonDetails as response entity sent over HTTP in Json format
	 */

	@GetMapping(path = "/person/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDetails>  findByIdPersonDetails(@PathVariable("id") Long personDetailsId){
		if(personDetailsId!= null && personDetailsId != zeroVal){

			Optional<PersonDetails> PersonDetails = personDetailsService.getOnePersonDetailsDetails(personDetailsId);

			if(PersonDetails.isPresent())
				return new ResponseEntity<PersonDetails>(PersonDetails.get(), HttpStatus.OK);
			else
				return new ResponseEntity<PersonDetails>(HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<PersonDetails>( HttpStatus.BAD_REQUEST);
		}
	}
	/**
	 *  
	 *  This method returns all person records from database.
	 *  
	 * @return Collection of PersonDetails as response entity sent over HTTP in Json format
	 */
	@GetMapping(path="/findAll",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<PersonDetails>> findAllPersonDetailss(){
		List<PersonDetails> PersonDetailss = personDetailsService.findAllPersonDetailss();
		return new ResponseEntity<Collection<PersonDetails>>(PersonDetailss, HttpStatus.OK);
	}

	/**
	 * 
	 * This method checks for URL path variable and records present for that person id, if present the deletes record from system
	 * 
	 * @param personDetailsId
	 * @return 
	 */
	@RequestMapping(path = "/person/{id}",
			method = RequestMethod.DELETE)
	public ResponseEntity<PersonDetails> removePersonDetails(@PathVariable("id") Long personDetailsId) {
		if(personDetailsId != null && personDetailsId != zeroVal){
			Optional<PersonDetails> PersonDetails=personDetailsService.getOnePersonDetailsDetails(personDetailsId);

			if(PersonDetails.isPresent()){
				personDetailsService.removePersonDetails(personDetailsId);
				return new ResponseEntity<PersonDetails>(HttpStatus.OK);
			}else{
				return new ResponseEntity<PersonDetails>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<PersonDetails>(HttpStatus.BAD_REQUEST);
		}

	}
}
