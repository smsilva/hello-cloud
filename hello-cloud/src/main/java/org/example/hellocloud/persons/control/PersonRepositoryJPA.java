package org.example.hellocloud.persons.control;

import java.math.BigInteger;
import org.example.hellocloud.infra.BaseRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.example.hellocloud.infra.Repository;
import org.example.hellocloud.persons.entity.Person;

@Stateless
@Repository
public class PersonRepositoryJPA implements BaseRepository<Person>, PersonRepository {

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
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isNameTaken(String name) {
	List<BigInteger> resultList = em
		.createNativeQuery("SELECT COUNT(*) FROM person p WHERE UPPER(p.name) = UPPER(:NAME)")
		.setParameter("NAME", name)
		.getResultList();
	
	BigInteger result = resultList.get(0);

	return result.intValue() > 0;
    }

}
