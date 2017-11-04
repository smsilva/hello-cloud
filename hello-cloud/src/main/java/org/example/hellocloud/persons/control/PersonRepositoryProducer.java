package org.example.hellocloud.persons.control;
public class PersonRepositoryProducer {
    
//    @Inject
    PersonRepositoryJPA jpaRepository;
    
//    @Produces
    public PersonRepository exposes() {
	return jpaRepository;
    }

}
