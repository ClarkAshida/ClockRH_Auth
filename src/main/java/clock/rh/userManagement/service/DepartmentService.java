package clock.rh.userManagement.service;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import clock.rh.userManagement.dto.department.DetailDepartmentDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DetailDepartmentDataDTO updateDepartment(Long id, CreateNewDepartmentDataDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));

        // Atualização segura apenas quando o campo não está vazio
        if (dto.name() != null && !dto.name().isBlank()) {
            department.setName(dto.name());
        }

        // Salvar a entidade atualizada
        departmentRepository.save(department);

        // Retornar um DTO detalhado para a resposta
        return new DetailDepartmentDataDTO(department);
    }
}
