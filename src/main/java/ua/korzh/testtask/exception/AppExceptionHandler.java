package ua.korzh.testtask.exception;

import com.google.common.util.concurrent.UncheckedExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({NoSuchLocationException.class, NoSuchElementException.class, UncheckedExecutionException.class})
    public ResponseEntity<?> handleException(RuntimeException e, WebRequest request) {

        return new ResponseEntity<>(new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false)),
                                    HttpStatus.CONFLICT);
    }
}
