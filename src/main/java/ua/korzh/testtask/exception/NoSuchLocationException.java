package ua.korzh.testtask.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NoSuchLocationException extends RuntimeException{

   private String message;

   public NoSuchLocationException(String message) {

      super(message);
   }
}
