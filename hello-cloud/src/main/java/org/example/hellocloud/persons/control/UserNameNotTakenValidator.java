package org.example.hellocloud.persons.control;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameNotTakenValidator implements ConstraintValidator<UserNameNotTaken, String> {

    @Inject
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
