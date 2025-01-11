package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.User;

public record ListUserDataDTO (
        String name,
        String email,
        String cpf,
        String department
){
    public ListUserDataDTO(User user) {
        this(user.getName(), user.getEmail(), user.getCpf(), user.getDepartment().getName());
    }
}
