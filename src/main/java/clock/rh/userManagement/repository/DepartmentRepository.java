package clock.rh.userManagement.repository;

import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
