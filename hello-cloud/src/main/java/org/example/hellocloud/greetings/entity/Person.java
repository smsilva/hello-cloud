package org.example.hellocloud.greetings.entity;

import javax.validation.constraints.NotNull;

public class Person {

    private Long id;
    private String name;
    
    @NotNull
    private String sexo;

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

    public String getSexo() {
	return sexo;
    }

    public void setSexo(String sexo) {
	this.sexo = sexo;
    }

}
