package edu.mum.coffee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.coffee.domain.Person;
import edu.mum.coffee.repository.PersonRepository;

@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> findByEmail(String email) {
		return personRepository.findByEmail(email);
	}
	
	public List<Person> findAllPerson(){
		return personRepository.findAll();
	}

	public Person findById(Long id) {
		return personRepository.findOne(id);
	}

	public void removePerson(Person person) {
		personRepository.delete(person);
	}
	public void remove(long id){
		personRepository.delete(id);
	}
	
	public Person getCurPerson(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    List<Person> persons = personRepository.findByEmail(name);
	    return persons.isEmpty()?null:persons.get(0);
	}
	public String getCurUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth.getName();
	}

}
