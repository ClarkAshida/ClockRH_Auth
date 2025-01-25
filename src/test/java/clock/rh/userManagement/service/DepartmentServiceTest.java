package clock.rh.userManagement.service;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import clock.rh.userManagement.dto.department.DetailDepartmentDataDTO;
import clock.rh.userManagement.dto.users.UserRoleDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.model.UserRole;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DepartmentService departmentService;
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
    @DisplayName("Testing updateDepartment method")
    void testUpdateDepartment() {
        // Setup
        CreateNewDepartmentDataDTO dto = new CreateNewDepartmentDataDTO("IT");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Run the test
        DetailDepartmentDataDTO result = departmentService.updateDepartment(1L, dto);

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("IT");
    }

    @Test
    @DisplayName("Testing updateDepartment method with invalid name")
    void testUpdateDepartmentWithInvalidName() {
        // Setup
        CreateNewDepartmentDataDTO dto = new CreateNewDepartmentDataDTO("");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        // Run the test
        assertThatThrownBy(() -> departmentService.updateDepartment(1L, dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O nome do setor não pode estar vazio");
    }

    @Test
    @DisplayName("Testing updateDepartment method with invalid ID")
    void testUpdateDepartmentWithInvalidId() {
        // Setup
        CreateNewDepartmentDataDTO dto = new CreateNewDepartmentDataDTO("IT");
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> departmentService.updateDepartment(1L, dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Setor com ID 1 não foi encontrado");
    }

    @Test
    @DisplayName("Testing getUsersByDepartment method")
    void testGetUsersByDepartment() {
        // Setup
        when(departmentRepository.existsById(1L)).thenReturn(true);

        // Mock users
        User user1 = new User();
        user1.setId(1L);
        user1.setName("João Silva");
        user1.setEmail("joao.silva@example.com");
        user1.setRole(UserRole.COLABORADOR);
        user1.setDepartment(department);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Maria Souza");
        user2.setEmail("maria.souza@example.com");
        user2.setRole(UserRole.RH);
        user2.setDepartment(department);

        // Return users in a pageable result
        Page<User> userPage = new PageImpl<>(List.of(user1, user2));
        when(userRepository.findByDepartmentId(eq(1L), any(PageRequest.class))).thenReturn(userPage);

        // Run the test
        Page<UserRoleDataDTO> result = departmentService.getUsersByDepartment(1L, PageRequest.of(0, 10));

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).name()).isEqualTo("João Silva");
        assertThat(result.getContent().get(1).name()).isEqualTo("Maria Souza");

        verify(departmentRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).findByDepartmentId(eq(1L), any(PageRequest.class));
    }

    @Test
    @DisplayName("Testing getUsersByDepartment method with invalid ID")
    void testGetUsersByDepartmentWithInvalidId() {
        // Setup
        when(departmentRepository.existsById(1L)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> departmentService.getUsersByDepartment(1L, PageRequest.of(0, 10)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Setor com ID 1 não foi encontrado");

        verify(departmentRepository, times(1)).existsById(1L);
        verify(userRepository, never()).findByDepartmentId(eq(1L), any(PageRequest.class));
    }

    @Test
    @DisplayName("Testing getManagersByDepartment method")
    void testGetManagersByDepartment() {
        // Setup
        when(departmentRepository.existsById(1L)).thenReturn(true);

        // Mock users
        User user1 = new User();
        user1.setId(1L);
        user1.setName("João Silva");
        user1.setEmail("joao.silva@gmail.com");
        user1.setRole(UserRole.COLABORADOR);
        user1.setDepartment(department);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Maria Souza");
        user2.setEmail("maria.souza@gmail.com");
        user2.setRole(UserRole.GESTOR);
        user2.setDepartment(department);

        // Return users in a pageable result
        Page<User> userPage = new PageImpl<>(List.of(user2));
        when(userRepository.findManagersByDepartmentId(eq(1L), any(PageRequest.class))).thenReturn(userPage);

        // Run the test
        Page<UserRoleDataDTO> result = departmentService.getManagersByDepartment(1L, PageRequest.of(0, 10));

        // Verify the results
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Maria Souza");

        verify(departmentRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).findManagersByDepartmentId(eq(1L), any(PageRequest.class));
    }


}
