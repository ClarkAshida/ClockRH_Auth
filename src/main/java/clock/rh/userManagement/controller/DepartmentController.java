package clock.rh.userManagement.controller;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import clock.rh.userManagement.dto.department.DetailDepartmentDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.service.DepartmentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;

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

    @GetMapping
    public ResponseEntity<Page<DetailDepartmentDataDTO>> listDepartments(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = departmentRepository.findAll(pagination).map(DetailDepartmentDataDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailDepartmentDataDTO> detailDepartment(@PathVariable Long id) {
        var department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este setor não foi encontrado"));
        return ResponseEntity.ok(new DetailDepartmentDataDTO(department));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetailDepartmentDataDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody @Valid CreateNewDepartmentDataDTO updateDepartmentDataDTO) {

        DetailDepartmentDataDTO updatedDepartment = departmentService.updateDepartment(id, updateDepartmentDataDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        var department = departmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este setor não foi encontrado"));
        departmentRepository.delete(department);
        return ResponseEntity.ok().build();
    }
}
