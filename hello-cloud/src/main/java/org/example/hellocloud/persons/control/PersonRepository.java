package org.example.hellocloud.persons.control;

import org.example.hellocloud.persons.entity.Person;

public interface PersonRepository extends Repository<Person> {

    boolean isNameTaken(String name);
    
}
