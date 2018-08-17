package com.dummy.contactmanager;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.dummy.contactmanager.controller.PersonDetailsController;
import com.dummy.contactmanager.entity.Address;
import com.dummy.contactmanager.entity.PersonDetails;
import com.dummy.contactmanager.entity.Work;
import com.dummy.contactmanager.service.PersonDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersonDetailsController.class })
@WebAppConfiguration
@EnableWebMvc
public class ContactmanagerApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@MockBean
	private PersonDetailsService service;

	@Test
	public void givenStatesReturnJsonArray()
			throws Exception {
		PersonDetails personDetails = getDummyPersonDetailsObject();

		when(service.findAllPersonDetailss()).thenReturn(Arrays.asList(personDetails));

		mockMvc.perform(get("/api/personDetails/findAll")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].firstName", is(personDetails.getFirstName())))
		.andExpect(jsonPath("$[0].lastName", is(personDetails.getLastName())))
		.andExpect(jsonPath("$[0].address.address", is(personDetails.getAddress().getAddress())))
		.andExpect(jsonPath("$[0].address.address2", is(personDetails.getAddress().getAddress2())))
		.andExpect(jsonPath("$[0].address.district", is(personDetails.getAddress().getDistrict())))
		.andExpect(jsonPath("$[0].address.postalCode", is(personDetails.getAddress().getPostalCode())))
		.andExpect(jsonPath("$[0].work.comapnyName", is(personDetails.getWork().getComapnyName())))
		.andExpect(jsonPath("$[0].work.companyEmailId", is(personDetails.getWork().getCompanyEmailId())));
		
		verify(service, times(1)).findAllPersonDetailss();
		verifyNoMoreInteractions(service);
	}

	@Test
	public void findById_EntryFound_ShouldReturnHttpStatusCode200() throws Exception {

		PersonDetails personDetails =getDummyPersonDetailsObject();
		Optional<PersonDetails> personDetailsOpt = Optional.of(personDetails);

		when(service.getOnePersonDetailsDetails(1L)).thenReturn((personDetailsOpt));

		mockMvc.perform(get("/api/personDetails/person/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk());


		verify(service, times(1)).getOnePersonDetailsDetails(1L);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void findById_EntryNotFound_ShouldReturnHttpStatusBadReq() throws Exception {

		when(service.getOnePersonDetailsDetails(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/personDetails/person/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());

		verify(service, times(1)).getOnePersonDetailsDetails(1L);
		verifyNoMoreInteractions(service);
	}
	
	@Test
    public void test_create_state_success() throws Exception {
		PersonDetails personDetails =getDummyPersonDetailsObject();

        when(service.getPersonDetailsBasedOnFirstNameAndLastName(personDetails)).thenReturn(null);
        when(service.savePersonDetails(any(PersonDetails.class))).thenReturn(personDetails);

        mockMvc.perform(
                post("/api/personDetails/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDetails)))
                .andExpect(status().isCreated());

        verify(service, times(1)).getPersonDetailsBasedOnFirstNameAndLastName(personDetails);
        verify(service, times(1)).savePersonDetails(personDetails);
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_create_state_fail_409_not_found() throws Exception {
		PersonDetails personDetails =getDummyPersonDetailsObject();

		 when(service.getPersonDetailsBasedOnFirstNameAndLastName(personDetails)).thenReturn(personDetails);

        mockMvc.perform(
                post("/api/personDetails/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDetails)))
                .andExpect(status().isConflict());

        verify(service, times(1)).getPersonDetailsBasedOnFirstNameAndLastName(personDetails);
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_delete_state_success() throws Exception {

		PersonDetails personDetails =getDummyPersonDetailsObjectWithId();
		Optional<PersonDetails> personDetailsOpt = Optional.of(personDetails);
		
        when(service.getOnePersonDetailsDetails(personDetails.getPersonId())).thenReturn(personDetailsOpt);
        doNothing().when(service).removePersonDetails(personDetails.getPersonId());

        mockMvc.perform(
                delete("/api/personDetails/person/{id}",personDetails.getPersonId()))
                .andExpect(status().isOk());

        verify(service, times(1)).getOnePersonDetailsDetails(personDetails.getPersonId());
        verify(service, times(1)).removePersonDetails(personDetails.getPersonId());
        verifyNoMoreInteractions(service);
    }
	
	@Test
    public void test_delete_state_fail_409_not_found() throws Exception {
		PersonDetails personDetails =getDummyPersonDetailsObjectWithId();

		when(service.getPersonDetailsBasedOnFirstNameAndLastName(personDetails)).thenReturn(null);

        mockMvc.perform(
        		delete("/api/personDetails/person/{id}", personDetails.getPersonId()))
                .andExpect(status().isNotFound());

    }	
	
	
	@Test
    public void test_update_user_success() throws Exception {
		PersonDetails personDetails =getDummyPersonDetailsObject();

		 when(service.updatePersonDetails(any(PersonDetails.class))).thenReturn(personDetails);


        mockMvc.perform(
                put("/api/personDetails//update", personDetails.getPersonId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDetails)))
                .andExpect(status().isOk());

        verify(service, times(1)).updatePersonDetails(personDetails);
        verifyNoMoreInteractions(service);
    }
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private PersonDetails getDummyPersonDetailsObject() {
		
		Address address = new Address();
		address.setAddress("Address Line 1");
		address.setDistrict("Maharashtra");
		address.setPostalCode("400072");
		address.setAddress2("Address Line 2");
		
		Work work = new Work();
		work.setComapnyName("Company Name");
		work.setCompanyEmailId("email@company.com");
		
		PersonDetails personDetails = new PersonDetails();
		personDetails.setWork(work);
		personDetails.setAddress(address);
		personDetails.setFirstName("First_name");
		personDetails.setLastName("Last Name");
		
		return personDetails;
	}

	private PersonDetails getDummyPersonDetailsObjectWithId() {
		
		Address address = new Address();
		address.setAddress("Address Line 1");
		address.setDistrict("Maharashtra");
		address.setPostalCode("400072");
		address.setAddress2("Address Line 2");
		
		Work work = new Work();
		work.setComapnyName("Company Name");
		work.setCompanyEmailId("email@company.com");
		
		PersonDetails personDetails = new PersonDetails();
		personDetails.setWork(work);
		personDetails.setAddress(address);
		personDetails.setFirstName("First_name");
		personDetails.setLastName("Last Name");
		personDetails.setPersonId(1l);
		return personDetails;
	}
}
