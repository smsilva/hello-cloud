package org.example.hellocloud.persons.control;

import org.example.hellocloud.infra.BaseRepository;
import org.example.hellocloud.persons.entity.Person;

public interface PersonRepository extends BaseRepository<Person> {

    boolean isNameTaken(String name);
    
}
