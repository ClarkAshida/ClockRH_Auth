package clock.rh.userManagement.repository;

import clock.rh.userManagement.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByActiveTrue(Pageable pagination);
    Page<User> findByDepartmentId(Long departmentId, Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.department.id = :departmentId AND u.role = 'GESTOR'")
    Page<User> findManagersByDepartmentId(Long departmentId, Pageable pageable);

    UserDetails findByEmail(String email);
}
