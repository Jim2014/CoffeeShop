package edu.mum.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.domain.Address;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	@Autowired
	private PersonService personService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Person> persons =personService.findAllPerson();
		model.addAttribute("persons", persons);
		return "personList";
	}
	
	@PostMapping("/add")
	public String add(Person person,Address address) {
		person.setAddress(address);
		personService.savePerson(person);
		return "redirect:/person/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		personService.remove(id);
		return "redirect:/person/list";
	}

	
}
