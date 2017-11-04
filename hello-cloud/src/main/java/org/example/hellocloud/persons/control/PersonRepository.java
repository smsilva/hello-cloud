package org.example.hellocloud.persons.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import org.example.hellocloud.persons.entity.Person;

@ApplicationScoped
public class PersonRepository implements Repository<Person> {

    private Long idSequence;
    private Map<Long, Person> list;

    public PersonRepository() {
	this.list = new HashMap<>();
	this.idSequence = Long.parseLong("0");
    }

    @Override
    public Person insert(Person person) throws Exception {
	if (person.getId() == null) {
	    this.idSequence++;
	    person.setId(this.idSequence);
	}

	this.list.put(idSequence, person);

	return this.findById(person.getId());
    }

    @Override
    public Person findById(Long id) {
	return this.list.get(id);
    }

    @Override
    public Person update(Person person) throws Exception {
	this.list.put(person.getId(), person);
	return person;
    }

    @Override
    public Person delete(Long id) throws Exception {
	Person p = this.list.get(id);
	this.list.remove(id);
	return p;
    }

    @Override
    public List<Person> listAll() {
	return new ArrayList<>(this.list.values());
    }

    public boolean isNameTaken(String name) {
	Optional<Person> person = this.listAll()
		.stream()
		.filter(p -> p.getName().equalsIgnoreCase(name))
		.findFirst();

	return person.isPresent();
    }

    @Override
    public Person delete(Person e) throws Exception {
	if (e != null) {
	    return this.delete(e.getId());
	}
	
	return null;
    }

}
