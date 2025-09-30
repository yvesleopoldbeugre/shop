package com.asking.api_produit.exceptions;

import com.asking.api_produit.utils.ErrorBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class HandlingException {
    List<String> listErrors = new ArrayList<>();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(500)
                .message("Une erreur est survenue, veillez réessayez plus tard")
                .build();
        return ResponseEntity.status(500).body(errorBody);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(403)
                .message("Vous n'avez pas les droits suffisant pour accéder à cette resource")
                .build();
        return ResponseEntity.status(403).body(errorBody);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e){
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(403)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(403).body(errorBody);
    }

    @ExceptionHandler(BadCredentialsExceptions.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsExceptions e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(401)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(401).body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(getStackTrace(e));
        for (ObjectError field: e.getAllErrors()){
            System.out.println(field.getObjectName()+": "+field.getDefaultMessage());
            listErrors.add(field.getDefaultMessage());
        }
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(400)
                .message("Le formulaire ne peut être transmis, veillez vérifiez vos informations.")
                .errorData(String.join(", ", listErrors))
                .build();
        return ResponseEntity.status(400).body(errorBody);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<?> handleServiceUnavailableException(ServiceUnavailableException e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(400)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(errorBody);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(404)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(404).body(errorBody);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(500)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(500).body(errorBody);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        log.error(getStackTrace(e));
        ErrorBody errorBody = ErrorBody.builder()
                .statusCode(400)
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(400).body(errorBody);
    }

    public String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
}
