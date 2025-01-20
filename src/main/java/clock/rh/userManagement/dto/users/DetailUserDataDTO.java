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
        this(
                user.getId(),
                user.getName() != null ? user.getName() : "Sem Nome",
                user.getEmail() != null ? user.getEmail() : "Sem Email",
                user.getCpf() != null ? user.getCpf() : "Sem CPF",
                user.getRole(),
                user.getDepartment() != null ? user.getDepartment().getName() : "Sem Departamento",
                user.getAdmissionDate() != null ? user.getAdmissionDate() : LocalDateTime.MIN,
                user.isActive()
        );
    }
}