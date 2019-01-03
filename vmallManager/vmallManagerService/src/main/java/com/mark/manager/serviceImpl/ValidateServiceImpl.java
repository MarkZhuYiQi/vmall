package com.mark.manager.serviceImpl;

import com.mark.manager.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;


import java.util.ArrayList;
import java.util.List;

@Service
public class ValidateServiceImpl<T> implements ValidateService {

    private T dto;

    @Autowired
    Validator localValidator;

    public T getDto() {
        return dto;
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    public List<String> validateDto() {
        return null;
    }
}
