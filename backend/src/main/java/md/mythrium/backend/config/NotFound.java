package md.mythrium.backend.config;


import jakarta.servlet.http.HttpServletRequest;
import md.mythrium.backend.json.output.ApiOutput;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NotFound {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiOutput> handle(NoHandlerFoundException ex, HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getRequestURI().toLowerCase().startsWith("/api")) {
            ApiOutput info = new ApiOutput(false, "this api does not exist");

            return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
        } else if(httpServletRequest.getRequestURI().toLowerCase().startsWith("/assets")) {
            ApiOutput info = new ApiOutput(false, "this asset does not exist");
            return new ResponseEntity<>(info, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new ApiOutput(false, "uri does not exist"), HttpStatus.NOT_FOUND);
    }
}
