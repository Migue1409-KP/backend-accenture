package co.accenture.pruebatecnica.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public final class Response<T> {
    private HttpStatus status;
    private List<T> data;
    private String message;

    public Response() {
        super();
        setStatus(HttpStatus.BAD_REQUEST);
        setData(new ArrayList<>());
        setMessage("");
    }
}
