package co.accenture.pruebatecnica.domain.model;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {

    @NotNull
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String name;
}
