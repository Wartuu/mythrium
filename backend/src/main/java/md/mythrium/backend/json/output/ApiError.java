package md.mythrium.backend.json.output;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiError extends ApiOutput {
    public final HttpStatus status;

    public ApiError(boolean success, String information, HttpStatus status) {
        super(success, information);
        this.status = status;
    }
}
