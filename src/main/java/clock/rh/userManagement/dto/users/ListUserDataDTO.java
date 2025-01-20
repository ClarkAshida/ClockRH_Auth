package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.User;

public record ListUserDataDTO (
        String name,
        String email,
        String cpf,
        String department
){
    public ListUserDataDTO(User user) {
        this(
                user.getName() != null ? user.getName() : "Sem Nome",
                user.getEmail() != null ? user.getEmail() : "Sem Email",
                user.getCpf() != null ? user.getCpf() : "Sem CPF",
                user.getDepartment() != null ? user.getDepartment().getName() : "Sem Departamento"
        );
    }
}
