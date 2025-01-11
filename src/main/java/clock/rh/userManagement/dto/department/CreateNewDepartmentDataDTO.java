package clock.rh.userManagement.dto.department;

import jakarta.validation.constraints.NotBlank;

public record CreateNewDepartmentDataDTO(
        @NotBlank
        String name
) {
}
