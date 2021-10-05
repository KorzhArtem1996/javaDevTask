package ua.korzh.testtask.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorDetails {

    private Date data;
    private String message;
    private String description;
}
