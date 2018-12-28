package com.mark.manager.validator;

import com.mark.manager.dto.Courses;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

public class ValidateDTO<T> {
    private T dto;

    public ValidateDTO(T dto) {
        this.dto = dto;
    }
    public String proceedValidate() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(dto);
        Iterator<ConstraintViolation<T>> it = constraintViolationSet.iterator();
        System.out.println(constraintViolationSet.size());
        while(it.hasNext()) {
            String mes = it.next().getMessage();
            System.out.println(mes);
            return mes;
        }
        return null;
    }
}
