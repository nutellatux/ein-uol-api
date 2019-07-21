package com.ein.uol.handler;


import com.ein.uol.exception.DetailsError;
import com.ein.uol.exception.LocationNotFoundException;
import com.ein.uol.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<DetailsError> handlerUserNotFoundException
            (UserNotFoundException e, HttpServletRequest request){

        DetailsError error = new DetailsError();
        error.setStatus(404L);
        error.setTitle("User Not Found.");
        error.setMessageDev("User Not Found.");
        error.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<DetailsError> handlerLocationNotFoundException
            (LocationNotFoundException e, HttpServletRequest request){

        DetailsError error = new DetailsError();
        error.setStatus(404L);
        error.setTitle("Location Not Found.");
        error.setMessageDev("Location Not Found.");
        error.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
