package org.example.hellocloud.persons.entity.validation;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.example.hellocloud.infra.Repository;
import org.example.hellocloud.persons.control.PersonRepository;

public class UserNameNotTakenValidator implements ConstraintValidator<UserNameNotTaken, String> {

    @Inject @Repository
    PersonRepository personRepository;

    @Override
    public void initialize(UserNameNotTaken constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
	boolean isNameTaken = personRepository.isNameTaken(value);
	boolean isValid = !isNameTaken;
	return isValid;
    }

}
