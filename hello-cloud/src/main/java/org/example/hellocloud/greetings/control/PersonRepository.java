package org.example.hellocloud.greetings.control;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import org.example.hellocloud.greetings.entity.Person;

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

}
