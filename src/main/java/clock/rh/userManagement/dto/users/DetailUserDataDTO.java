package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.UserRole;
import clock.rh.userManagement.model.User;

import java.time.LocalDateTime;

public record DetailUserDataDTO(
        Long id,
        String name,
        String email,
        String cpf,
        UserRole role,
        String department,
        LocalDateTime admissionDate,
        boolean active
) {
    public DetailUserDataDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getCpf(), user.getRole(), user.getDepartment().getName(), user.getAdmissionDate(), user.isActive());
    }
}