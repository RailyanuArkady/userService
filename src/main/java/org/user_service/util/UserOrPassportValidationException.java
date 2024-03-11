package org.user_service.util;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class UserOrPassportValidationException extends MethodArgumentNotValidException {

    /**
     * Constructor for {@link MethodArgumentNotValidException}.
     *
     * @param parameter     the parameter that failed validation
     * @param bindingResult the results of the validation
     */
    public UserOrPassportValidationException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
    }

}
