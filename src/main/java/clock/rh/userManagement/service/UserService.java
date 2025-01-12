package clock.rh.userManagement.service;

import clock.rh.userManagement.dto.users.DetailUserDataDTO;
import clock.rh.userManagement.dto.users.UpdateUserDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public DetailUserDataDTO updateUserInfo(Long id, UpdateUserDataDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Atualização condicional apenas se os campos não forem nulos
        if (dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            user.setEmail(dto.email());
        }
        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(dto.password());
        }

        // Somente atualiza o departamento se o ID for informado e válido
        if (dto.departmentId() != null) {
            Department department = departmentRepository.findById(dto.departmentId())
                    .orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
            user.setDepartment(department);
        }

        userRepository.save(user);
        return new DetailUserDataDTO(user);
    }

}
