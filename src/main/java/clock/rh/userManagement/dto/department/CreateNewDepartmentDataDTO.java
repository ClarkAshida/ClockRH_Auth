package clock.rh.userManagement.dto.department;

import jakarta.validation.constraints.NotBlank;

public record CreateNewDepartmentDataDTO(
        @NotBlank(message = "O nome do setor é obrigatório.")
        String name
) {
}
