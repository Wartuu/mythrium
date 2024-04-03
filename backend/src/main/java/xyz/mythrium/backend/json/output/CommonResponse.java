package xyz.mythrium.backend.json.output;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponse {
    public static final ResponseEntity<ApiOutput> INVALID_SESSION = new ResponseEntity<>(new ApiOutput(false, "invalid session"), HttpStatus.OK);
    public static final ResponseEntity<ApiOutput> NOT_AUTHORIZED = new ResponseEntity<>(new ApiOutput(false, "no authorization"), HttpStatus.UNAUTHORIZED);
    public static final ResponseEntity<ApiOutput> TOO_LARGE_INPUT = new ResponseEntity<>(new ApiOutput(false, "one of values is too large"), HttpStatus.BAD_REQUEST);
    public static final ResponseEntity<ApiOutput> RESOURCE_NOT_FOUND = new ResponseEntity<>(new ApiOutput(false, "resource does not exist"), HttpStatus.NOT_FOUND);

}
