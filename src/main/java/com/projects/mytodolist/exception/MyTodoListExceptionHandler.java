package com.projects.mytodolist.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class MyTodoListExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<CustomError> handleNotFoundExceptions(Exception e, WebRequest request) {
        CustomError error = new CustomError(e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<CustomError> handleTransactionSystemException(Exception e, WebRequest request) {
        String userMessage = "Falha na validação dos dados, verifique se os dados estão corretos e tente novamente.";
        String devMessage = "Error: '%s' at: %s".formatted(e.getLocalizedMessage(), request.getDescription(false));
        CustomError error = new CustomError(userMessage, devMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<CustomError> errorList = getErrorsList(ex.getBindingResult());
        return handleExceptionInternal(ex, errorList, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<CustomError> getErrorsList(BindingResult bindingResult) {
        List<CustomError> errorList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMsg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String devMsg = fieldError.toString();
            errorList.add(new CustomError(userMsg, devMsg));
        }
        return errorList;
    }
}
