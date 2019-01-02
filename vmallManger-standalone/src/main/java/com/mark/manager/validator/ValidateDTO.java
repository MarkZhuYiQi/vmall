package com.mark.manager.validator;

import com.mark.manager.dto.Courses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ValidateDTO<T> {
    @Autowired
    Validator validator;
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
    public String validatePojo() {
        DataBinder binder = new DataBinder(dto);
        binder.setValidator(validator);
        binder.validate();
        BindingResult results = binder.getBindingResult();
        List<ObjectError> list = results.getAllErrors();
        System.out.println(list.toString());
        return null;
    }
}
