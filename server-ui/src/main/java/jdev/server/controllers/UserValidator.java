package jdev.server.controllers;

import jdev.server.services.UsersService;
import jdev.users.User;
import org.apache.coyote.ErrorState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.FixedKeySet;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Component
public class UserValidator implements Validator {

    @Autowired
    UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.client.username");

        if(((User) target).getUsername() != null) {
            if ((((User) target).getUsername().length() < 3) ||
                (((User) target).getUsername().length() > 20)) {
                System.out.println("Length = " + ((User) target).getUsername().length());
                errors.rejectValue("username", "Length.client.username");
            }
        }
    }
}
