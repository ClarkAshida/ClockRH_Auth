package clock.rh.userManagement.controller;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import clock.rh.userManagement.dto.department.DetailDepartmentDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    // ✅ Criar um novo departamento (POST)
    @PostMapping
    @Transactional
    public ResponseEntity<DetailDepartmentDataDTO> createDepartment(
            @RequestBody @Valid CreateNewDepartmentDataDTO newDepartmentDataDTO,
            UriComponentsBuilder uriBuilder) {

        var department = new Department();
        department.setName(newDepartmentDataDTO.name());
        departmentRepository.save(department);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(department.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailDepartmentDataDTO(department));
    }
}
