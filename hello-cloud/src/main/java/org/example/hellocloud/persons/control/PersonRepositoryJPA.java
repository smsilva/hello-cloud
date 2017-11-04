package org.example.hellocloud.persons.control;

import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.example.hellocloud.persons.boundary.Silvio;
import org.example.hellocloud.persons.entity.Person;

@Stateless
@Dependent
public class PersonRepositoryJPA implements Repository<Person>, PersonRepository {

    @PersistenceContext
    EntityManager em;

    public PersonRepositoryJPA() {
    }

    @Override
    public Person insert(Person e) throws Exception {
	em.persist(e);
	return e;
    }

    @Override
    public Person findById(Long id) {
	return em.find(Person.class, id);
    }

    @Override
    public Person update(Person e) throws Exception {
	return em.merge(e);
    }

    @Override
    public Person delete(Person e) throws Exception {
	if (e != null) {
	    em.remove(e);
	}
	return e;
    }

    @Override
    public Person delete(Long id) throws Exception {
	Person e = this.findById(id);
	return this.delete(e);
    }

    @Override
    public List<Person> listAll() {
	return em.createQuery("SELECT p FROM Person p", Person.class)
		.getResultList();
    }

    @Override
    public boolean isNameTaken(String name) {
	return !em.createQuery("SELECT p FROM Person p WHERE p.name = :NAME", Person.class)
		.setParameter("NAME", name)
		.getResultList()
		.isEmpty();
    }

}
