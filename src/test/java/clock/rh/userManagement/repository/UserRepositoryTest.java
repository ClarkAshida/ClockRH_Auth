package clock.rh.userManagement.repository;

import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.model.UserRole;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    private UserRole userRole;

    @Test
    @DisplayName("Should return all users that are active")
    void testFindAllByActiveTrue() {
        // Setup
        // Create a department
        Department department = createDepartment("TI", new ArrayList<>(), new ArrayList<>());
        // Create an active user
        User activeUser = createUser("John Doe", "john.doe@gmail.com", "123456", "12345678901", LocalDateTime.now(), UserRole.COLABORADOR, department);
        // Create an inactive user
        User inactiveUser = createUser("Ashley Chris", "ashley.chris@gmail.com", "123456", "12345678902", LocalDateTime.now(), UserRole.COLABORADOR, department);
        inactiveUser.setActive(false);

        // Test
        Page<User> result = userRepository.findAllByActiveTrue(PageRequest.of(0, 10));

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).isActive()).isTrue();
    }

    @Test
    @DisplayName("Should return all users from a department based on the department id")
    void shouldReturnUsersByDepartmentId() {
        // Setup
        // Create two different departments
        Department ITdepartment = createDepartment("TI", new ArrayList<>(), new ArrayList<>());
        Department HRdepartment = createDepartment("RH", new ArrayList<>(), new ArrayList<>());
        // Create a user for each department
        User ITUser = createUser("John Doe", "john.doe@gmail.com", "123456", "12345678901", LocalDateTime.now(), UserRole.COLABORADOR, ITdepartment);
        User HRUser = createUser("Ashley Chris", "ashley.chris@gmail.com", "123456", "12345678902", LocalDateTime.now(), UserRole.COLABORADOR, HRdepartment);

        //Test
        Page<User> result = userRepository.findByDepartmentId(ITdepartment.getId(), PageRequest.of(0, 10));

        //Assert
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getDepartment().getName()).isEqualTo("TI");
    }

    @Test
    @DisplayName("Should return all managers from a department based on the department id")
    void shouldReturnManagersByDepartmentId() {
        // Setup
        // Create two different departments
        Department ITdepartment = createDepartment("TI", new ArrayList<>(), new ArrayList<>());
        Department HRdepartment = createDepartment("RH", new ArrayList<>(), new ArrayList<>());
        // Create a user with the "COLABORADOR role" for each department
        User ITEmployee = createUser("John Doe", "john.doe@gmail.com", "123456", "12345678901", LocalDateTime.now(), UserRole.COLABORADOR, ITdepartment);
        User HREmployee = createUser("Ashley Chris", "ashley.chris@gmail.com", "123456", "12345678902", LocalDateTime.now(), UserRole.COLABORADOR, HRdepartment);
        // Create a user with the "GESTOR role" for each department
        User ITManager = createUser("Ryan Gosling", "ryan.gosling@gmail.com", "123456", "12345678903", LocalDateTime.now(), UserRole.GESTOR, ITdepartment);
        User HRManager = createUser("Emma Stone", "emma.stone@gmail.com", "123456", "12345678904", LocalDateTime.now(), UserRole.GESTOR, HRdepartment);

        // Test
        Page<User> result = userRepository.findManagersByDepartmentId(ITdepartment.getId(), PageRequest.of(0, 10));

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).getRole()).isEqualTo(UserRole.GESTOR);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Ryan Gosling");
    }

    @Test
    @DisplayName("Should return a user by email")
    void shouldReturnUserByEmail() {
        // Setup
        // Create two different departments
        Department ITdepartment = createDepartment("TI", new ArrayList<>(), new ArrayList<>());
        Department HRdepartment = createDepartment("RH", new ArrayList<>(), new ArrayList<>());
        // Create a user for each department
        User ITUser = createUser("John Doe", "john.doe@gmail.com", "123456", "12345678901", LocalDateTime.now(), UserRole.COLABORADOR, ITdepartment);
        User HREmployee = createUser("Ashley Chris", "ashley.chris@gmail.com", "123456", "12345678902", LocalDateTime.now(), UserRole.COLABORADOR, HRdepartment);

        // Test
        UserDetails result = userRepository.findByEmail("john.doe@gmail.com");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("john.doe@gmail.com");
    }

    // Helper methods to create entities for testing

    private Department createDepartment(String name, ArrayList<User> users, ArrayList<User> managers) {
        Department department = new Department(name, users, managers);
        entityManager.persist(department);
        return department;
    }
    private User createUser(String name, String email, String password, String cpf, LocalDateTime admissionDate, UserRole role, Department department) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setCpf(cpf);
        user.setAdmissionDate(admissionDate);
        user.setRole(role);
        user.setDepartment(department);
        user.setActive(true);
        entityManager.persist(user);
        return user;
    }

}
