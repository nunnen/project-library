package com.vunnen.util;

import com.vunnen.dao.PersonDAO;
import com.vunnen.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personDAO.findPersonByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Имя пользователя уже занято");
        };
    }
}
