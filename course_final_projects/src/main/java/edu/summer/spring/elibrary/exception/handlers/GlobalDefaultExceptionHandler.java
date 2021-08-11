package edu.summer.spring.elibrary.exception.handlers;

import edu.summer.spring.elibrary.validation.ValidationErrorResponse;
import edu.summer.spring.elibrary.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ModelAndView handleConstraintViolationException(ConstraintViolationException e) {
        ModelAndView modelAndView = new ModelAndView("validation_error");
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        modelAndView.addObject("violations", error.getViolations());
        return modelAndView;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ModelAndView onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ModelAndView modelAndView = new ModelAndView("validation_error");
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        modelAndView.addObject("violations", error.getViolations());
        return modelAndView;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
    public ModelAndView onBindException(
            BindingResult e) {
        ModelAndView modelAndView = new ModelAndView("validation_error");
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        modelAndView.addObject("violations", error.getViolations());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        return "404";
    }
}
