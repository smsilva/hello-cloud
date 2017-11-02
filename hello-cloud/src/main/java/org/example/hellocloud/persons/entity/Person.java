package org.example.hellocloud.persons.entity;

import javax.validation.constraints.NotNull;
import org.example.hellocloud.persons.control.UserNameNotTaken;

public class Person {

    private Long id;
    
    @UserNameNotTaken
    private String name;

    @NotNull(message = "{person.gender.notnull}")
    private String gender;

    public Person() {
    }

    public Person(String name) {
	this.name = name;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

}
