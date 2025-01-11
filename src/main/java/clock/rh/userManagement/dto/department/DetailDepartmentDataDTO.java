package clock.rh.userManagement.dto.department;

import clock.rh.userManagement.model.Department;

public record DetailDepartmentDataDTO(
        Long id,
        String name
) {
    public DetailDepartmentDataDTO(Department department) {
        this(department.getId(), department.getName());
    }
}
