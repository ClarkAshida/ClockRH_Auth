package clock.rh.userManagement.service;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import clock.rh.userManagement.dto.department.DetailDepartmentDataDTO;
import clock.rh.userManagement.dto.users.UserRoleDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public DetailDepartmentDataDTO updateDepartment(Long id, CreateNewDepartmentDataDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor com ID " + id + " não foi encontrado"));

        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException("O nome do setor não pode estar vazio");
        }

        department.setName(dto.name());
        departmentRepository.save(department);

        return new DetailDepartmentDataDTO(department);
    }

    public Page<UserRoleDataDTO> getUsersByDepartment(Long departmentId, Pageable pageable) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Setor com ID " + departmentId + " não foi encontrado");
        }

        Page<UserRoleDataDTO> users = userRepository.findByDepartmentId(departmentId, pageable)
                .map(UserRoleDataDTO::new);

        if (users.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para o setor com ID " + departmentId);
        }

        return users;
    }

    public Page<UserRoleDataDTO> getManagersByDepartment(Long departmentId, Pageable pageable) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new EntityNotFoundException("Setor com ID " + departmentId + " não foi encontrado");
        }

        Page<UserRoleDataDTO> managers = userRepository.findManagersByDepartmentId(departmentId, pageable)
                .map(UserRoleDataDTO::new);

        if (managers.isEmpty()) {
            throw new EntityNotFoundException("Nenhum gerente encontrado para o setor com ID " + departmentId);
        }

        return managers;
    }
}
