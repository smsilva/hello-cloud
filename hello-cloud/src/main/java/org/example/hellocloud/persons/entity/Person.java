package org.example.hellocloud.persons.entity;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.example.hellocloud.persons.control.UserNameNotTaken;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UserNameNotTaken
    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull(message = "{person.gender.notnull}")
    @Size(max = 1)
    private String gender;

    @OneToMany(mappedBy = "person")
    private Map<Long, Contract> contracts;

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

    public Map<Long, Contract> getContracts() {
	return contracts;
    }

    public void setContracts(Map<Long, Contract> contracts) {
	this.contracts = contracts;
    }

}
