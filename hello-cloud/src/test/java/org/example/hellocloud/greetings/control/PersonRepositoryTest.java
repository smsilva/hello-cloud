package org.example.hellocloud.greetings.control;

import org.example.hellocloud.greetings.entity.Person;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PersonRepositoryTest {
    
    private PersonRepository personRepository;
    
    @Before
    public void before() {
	this.personRepository = new PersonRepository();
    }
    
    @Test
    public void insert() {
	Person person = new Person("Silvio Silva");
	
	Person inserted = personRepository.insert(person);
	
	assertNotNull(inserted);
	assertNotNull(inserted.getId());
	assertEquals("Silvio Silva", inserted.getName());
    }
    
    @Test
    public void find() {
	insert();
	
	Person person = personRepository.findById(Long.parseLong("1"));
	
	assertNotNull(person);
	assertEquals(Long.parseLong("1"), person.getId(), 0.1);
	assertEquals("Silvio Silva", person.getName());
    }
    
    @Test
    public void update() {
	insert();
	
	Person person = personRepository.findById(Long.parseLong("1"));
	person.setName("Silvio Manoel da Silva");
	
	personRepository.update(person);
	
	person = personRepository.findById(Long.parseLong("1"));
	assertEquals("Silvio Manoel da Silva", person.getName());
    }
    
    @Test
    public void delete() {
	insert();
	
	Person person = personRepository.findById(Long.parseLong("1"));
	
	boolean deleted = personRepository.delete(person.getId());
	assertTrue(deleted);
	
	person = personRepository.findById(Long.parseLong("1"));
	assertTrue(person == null);
    }

}
