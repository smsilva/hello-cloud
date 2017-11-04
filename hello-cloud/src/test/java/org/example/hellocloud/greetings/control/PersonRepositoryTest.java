package org.example.hellocloud.greetings.control;

import org.example.hellocloud.persons.control.PersonRepository;
import java.util.List;
import org.example.hellocloud.persons.entity.Person;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    public void insert() throws Exception {
	Person person = new Person("Silvio Silva");

	Person inserted = personRepository.insert(person);

	assertNotNull(inserted);
	assertNotNull(inserted.getId());
	assertEquals("Silvio Silva", inserted.getName());
    }

    @Test
    public void find() throws Exception {
	insert();

	Person person = personRepository.findById(Long.parseLong("1"));

	assertNotNull(person);
	assertEquals(Long.parseLong("1"), person.getId(), 0.1);
	assertEquals("Silvio Silva", person.getName());
    }

    @Test
    public void update() throws Exception {
	insert();

	Person person = personRepository.findById(Long.parseLong("1"));
	person.setName("Silvio Manoel da Silva");

	personRepository.update(person);

	person = personRepository.findById(Long.parseLong("1"));
	assertEquals("Silvio Manoel da Silva", person.getName());
    }

    @Test
    public void delete() throws Exception {
	insert();

	Person person = personRepository.findById(Long.parseLong("1"));

	Person deleted = personRepository.delete(person.getId());
	assertNotNull(deleted);

	person = personRepository.findById(Long.parseLong("1"));
	assertTrue(person == null);
    }

    @Test
    public void list() throws Exception {
	insert();
	List<Person> list = personRepository.listAll();
	assertTrue(list.size() > 0);
    }

    @Test
    public void nameAlreadyTaken() throws Exception {
	String name = "Silvio Silva";

	assertFalse(personRepository.isNameTaken(name));

	insert();

	assertTrue(personRepository.isNameTaken(name));
    }

}
