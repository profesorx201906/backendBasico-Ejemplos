package com.crud.grupo69.Exception.Exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crud.grupo69.Exception.Entity.Error;
import com.crud.grupo69.Exception.Entity.ErrorResponse;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(NumberFormatException ex) {

        Error error = new Error("Conversión de DAto", "El dato enviado no se puede convertir a numérico");
        List<Error> listError = new ArrayList<>();
        listError.add(error);
        return new ResponseEntity<>(new ErrorResponse(406, "Tipo de dato", new Date(), listError),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handledException(HttpMessageNotReadableException ex){
        Error error = new Error("Falta de información", "No viene el cuerpo de la petición");
        List<Error> listError = new ArrayList<>();
        listError.add(error);
        return new ResponseEntity<>(new ErrorResponse(404, "Datos", new Date(), listError),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handledException(DivisionZeroErrorException ex){
        Error error = new Error("Error tipo de dato", "No se puede dividir por 0");
        List<Error> listError = new ArrayList<>();
        listError.add(error);
        return new ResponseEntity<>(new ErrorResponse(400, "Datos", new Date(), listError),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handledException(NoFoundException ex){
        Error error = new Error("Dato no encontrado", ex.getMessage());
        List<Error> listError = new ArrayList<>();
        listError.add(error);
        return new ResponseEntity<>(new ErrorResponse(400, "Datos", new Date(), listError),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse>  handledException(InvalidDataException ex){
        
        List<Error> listError = new ArrayList<>();
        ex.getResult().getAllErrors().forEach((err)->{
            Error error = new Error();
            error.setField(((FieldError) err).getField());
            error.setMessage(err.getDefaultMessage());
            listError.add(error);
        });
        return new ResponseEntity<>(new ErrorResponse(400, "Error de Data", new Date(), listError),HttpStatus.BAD_REQUEST);
    }

}
