package edu.mum.coffee.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@RestController
@RequestMapping("/api/person/")
public class PersonAPI {

	@Autowired
	private PersonService personService;

	@GetMapping("/list")
	List<Person> list() {
		return personService.findAllPerson();
	}
	
	@PostMapping("/add")
	Person add(@RequestBody Person person){
		return personService.savePerson(person);
	}
	
	@PutMapping("/update")
	Person update(@RequestBody Person person){
		return personService.savePerson(person);
	}
	

	
}
