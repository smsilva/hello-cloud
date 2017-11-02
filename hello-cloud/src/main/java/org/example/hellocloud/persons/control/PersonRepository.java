package org.example.hellocloud.persons.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ejb.Singleton;
import org.example.hellocloud.persons.entity.Person;

@Singleton
public class PersonRepository {

    private Long idSequence;
    private Map<Long, Person> list;

    public PersonRepository() {
	this.list = new HashMap<>();
	this.idSequence = Long.parseLong("0");
    }

    public Person insert(Person person) {
	if (person.getId() == null) {
	    this.idSequence++;
	    person.setId(this.idSequence);
	}

	this.list.put(idSequence, person);

	return this.findById(person.getId());
    }

    public Person findById(Long id) {
	return this.list.get(id);
    }

    public void update(Person person) {
	this.list.put(person.getId(), person);
    }

    public boolean delete(Long id) {
	return this.list.remove(id) != null;
    }

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

}
