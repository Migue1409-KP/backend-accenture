package co.accenture.pruebatecnica.application.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(message = "Name must be between 3 and 100 characters", min = 3, max = 100)
    @Pattern(message = "Name must contain only letters and numbers", regexp = "^[a-zA-Z0-9 ]*$")
    private String name;

    @NotNull(message = "Branch ID is mandatory")
    private UUID branchId;

    @NotNull(message = "Amount is mandatory")
    @Min(message = "Amount must be positive", value = 0)
    private Integer amount;
}
