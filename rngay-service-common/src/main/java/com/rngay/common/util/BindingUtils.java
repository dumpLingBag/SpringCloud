package com.rngay.common.util;

import com.rngay.common.vo.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class BindingUtils {

    public static Result<?> bindingResult(BindingResult result){
        if (result.hasErrors()){
            StringBuilder errors = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError: fieldErrors) {
                errors.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage());
            }
            return Result.fail(errors.toString());
        }
        return Result.success();
    }

}
