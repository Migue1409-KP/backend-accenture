package co.accenture.pruebatecnica.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(message = "Name must be between 3 and 100 characters", min = 3, max = 100)
    @Pattern(message = "Name must contain only letters and numbers", regexp = "^[a-zA-Z0-9 ]*$")
    private String name;

    @NotNull(message = "Franchise ID is mandatory")
    private UUID franchiseId;
}
