package clock.rh.userManagement.service;

import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.model.UserRole;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;
    private Department department;
    private User user;

    @BeforeEach
    void setUp() {
        // Setup
        department = new Department();
        department.setId(1L);
        department.setName("IT");

        user = new User();
        user.setId(1L);
        user.setName("João Silva");
        user.setEmail("joao.silva@example.com");
        user.setPassword("senha123");
        user.setRole(UserRole.COLABORADOR);
        user.setActive(true);
        user.setDepartment(department);
    }

    @Test
    public void testLoadUserByUsername() {
        // Setup
        when(userRepository.findByEmail("joao.silva@example.com")).thenReturn(user);

        // Run the test
        UserDetails result = authService.loadUserByUsername("joao.silva@example.com");

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("joao.silva@example.com");
        verify(userRepository, times(1)).findByEmail("joao.silva@example.com");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Setup
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Run the test
        try {
            authService.loadUserByUsername("nonexistent@example.com");
        } catch (UsernameNotFoundException e) {
            // Verify the results
            assertThat(e).isInstanceOf(UsernameNotFoundException.class);
            assertThat(e.getMessage()).isEqualTo("User not found with email: nonexistent@example.com");
        }

        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }

}
