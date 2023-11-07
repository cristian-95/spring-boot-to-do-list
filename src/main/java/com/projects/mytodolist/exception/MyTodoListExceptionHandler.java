package com.projects.mytodolist.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class MyTodoListExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFoundException(EntityNotFoundException e) {

        System.out.println("sdsds".toUpperCase());

        CustomError error = new CustomError(e.getMessage(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
// formato
//    @ExceptionHandler(OtherCustomException.class)
//    public ResponseEntity<String> handleOtherCustomException(OtherCustomException e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//    }

    //
//    @Override
//    @ExceptionHandler(MissingPathVariableException.class)
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        CustomError customError = new CustomError(ex.getMessage(), ex.getRootCause().toString());
//        return handleExceptionInternal(ex, customError, headers, status, request);
//    }
//
//    @ExceptionHandler({EntityNotFoundException.class})
//    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
//        String userMsg = messageSource.getMessage("recurso.nao.encontrado", null, LocaleContextHolder.getLocale());
//        String devMsg = exception.getMessage();
//        CustomError error = new CustomError(userMsg, devMsg);
//
//        return ResponseEntity.badRequest().body(error);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public CustomError handleValidationExceptions(MethodArgumentNotValidException ex) {
//        return new CustomError(ex.getMessage(), ex.getCause().toString());
//    }

    public static class CustomError {
        private final String userMessage;
        private final String devMessage;

        public CustomError(String userMessage, String devMessage) {
            this.devMessage = devMessage;
            this.userMessage = userMessage;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public String getDevMessage() {
            return devMessage;
        }
    }
}
