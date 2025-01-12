package clock.rh.userManagement.dto.users;

import clock.rh.userManagement.model.User;
import clock.rh.userManagement.model.UserRole;

public record UserRoleDataDTO(
        Long id,
        String name,
        String cpf,
        UserRole role
) {
    public UserRoleDataDTO(User user) {
        this(user.getId(), user.getName(), user.getCpf(), user.getRole());
    }
}
