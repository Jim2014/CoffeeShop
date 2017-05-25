package edu.mum.coffee;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mum.coffee.domain.Address;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private PersonService personService;
	private Person[] testPerson;

	final static int TEST_NumberOfPersons = 3;

	@Before
	public void setUp() {

		// Remove previous objects
		deleteTestObjects();

		// Creating objects to use during the test
		testPerson = new Person[TEST_NumberOfPersons];

		for (int i = 0; i < TEST_NumberOfPersons; i++) {
			Person person = new Person();
			person.setEmail(i + "test@email.com");
			person.setEnable((i % 2 == 0));
			person.setFirstName("firstName Test" + i);
			person.setLastName("lastName Test" + i);
			person.setPhone("111888777" + i);
			person.setAddress(new Address());
			person.getAddress().setZipcode("5527" + i);
			person.getAddress().setCity("Fairfield" + i);
			person.getAddress().setState("IA" + i);
			person.getAddress().setCountry("USA" + i);
			testPerson[i] = person;
		}

		// Store test objects in database -- Skipping 0 for Create Test
		for (int i = 1; i < TEST_NumberOfPersons; i++) {
			testPerson[i] = personService.savePerson(testPerson[i]);
		}
	}

	@After
	public void tearDown() throws Exception {
		deleteTestObjects();
	}

	@Test
	public void testSavePerson() {
		Person stored = personService.savePerson(testPerson[0]);
		Person saved = personService.findById(stored.getId());
		if (!comparePerson(testPerson[0], saved)) {
			fail("Not storing or retrieving Person");
		}
	}

	@Test
	public void testFindByEmail() {
		List<Person> persons = personService.findByEmail("SOMETHING ELSE");
		if (!persons.isEmpty()) {
			fail("Not expected to match Person");
		}
		for (Person saved : personService.findByEmail(testPerson[1].getEmail())) {
			if (!saved.getEmail().equals(testPerson[1].getEmail())) {
				fail("Retrieved Person values are different");
			}
		}
	}

	@Test
	public void testFindById() {
		long something_else = testPerson[2].getId() + 100;

		Person saved = personService.findById(something_else);

		if (comparePerson(testPerson[1], saved)) {
			fail("Not expected to match Person");
		}

		saved = personService.findById(testPerson[2].getId());

		if (!comparePerson(testPerson[2], saved)) {
			fail("Retrieved Person values are different");
		}
	}

	private void deleteTestObjects() {
		for (int i = 0; i < TEST_NumberOfPersons; i++) {
			try {
				Person person = personService.findById(testPerson[i].getId());
				personService.removePerson(person);
			} catch (Exception e) {
				// Do not log exceptions
			}
		}
	}

	private boolean comparePerson(Person a, Person b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		if (!a.getFirstName().equals(b.getFirstName())) {
			return false;
		}
		if (!a.getLastName().equals(b.getLastName())) {
			return false;
		}
		if (!a.getEmail().equals(b.getEmail())) {
			return false;
		}
		if (!a.getPhone().equals(b.getPhone())) {
			return false;
		}
		Address aa = a.getAddress();
		Address ab = b.getAddress();
		if (!aa.getCity().equals(ab.getCity())) {
			return false;
		}
		if (!aa.getCountry().equals(ab.getCountry())) {
			return false;
		}
		if (!aa.getState().equals(ab.getState())) {
			return false;
		}
		if (!aa.getZipcode().equals(ab.getZipcode())) {
			return false;
		}
		return true;
	}

}
