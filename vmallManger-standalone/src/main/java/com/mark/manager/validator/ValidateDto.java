package com.mark.manager.validator;

import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class ValidateDto<T> {
    private Validator validator;
    private T dto;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    public ValidateDto(Validator validator, T dto) {
        this.validator = validator;
        this.dto = dto;
    }

    public ValidateDto() {}

    public List<String> validate() {
        List<String> errList = new ArrayList<String>();
        DataBinder binder = new DataBinder(dto);
        binder.setValidator(validator);
        binder.validate();
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            List<ObjectError> list = results.getAllErrors();
            for(ObjectError err : list) {
                errList.add(err.getDefaultMessage());
            }
        }
        return errList;
    }
}
