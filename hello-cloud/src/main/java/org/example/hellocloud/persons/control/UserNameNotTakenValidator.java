package org.example.hellocloud.persons.control;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameNotTakenValidator implements ConstraintValidator<UserNameNotTaken, String> {

    private static final Logger LOG = Logger.getLogger(UserNameNotTakenValidator.class.getName());

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
