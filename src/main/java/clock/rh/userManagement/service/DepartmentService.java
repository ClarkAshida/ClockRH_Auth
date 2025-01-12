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
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));

        if (dto.name() != null && !dto.name().isBlank()) {
            department.setName(dto.name());
        }

        departmentRepository.save(department);

        return new DetailDepartmentDataDTO(department);
    }

    public Page<UserRoleDataDTO> getUsersByDepartment(Long departmentId, Pageable pageable) {
        return userRepository.findByDepartmentId(departmentId, pageable)
                .map(UserRoleDataDTO::new);
    }

    public Page<UserRoleDataDTO> getManagersByDepartment(Long departmentId, Pageable pageable) {
        return userRepository.findManagersByDepartmentId(departmentId, pageable)
                .map(UserRoleDataDTO::new);
    }
}
