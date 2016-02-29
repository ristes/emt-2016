package mk.ukim.finki.emt.store.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler  {

    @ExceptionHandler
    @ResponseBody
    public void handle(Exception e) {
        e.printStackTrace();
    }
}
