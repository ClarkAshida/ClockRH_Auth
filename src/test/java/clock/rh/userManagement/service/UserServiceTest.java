package clock.rh.userManagement.service;

import clock.rh.userManagement.dto.users.DetailUserDataDTO;
import clock.rh.userManagement.dto.users.UpdateUserDataDTO;
import clock.rh.userManagement.model.Department;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.model.UserRole;
import clock.rh.userManagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;
    private Department department;

    @BeforeEach
    void setUp() {
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
    @DisplayName("Testing updateUserInfo method")
    public void testUpdateUserInfo() {

        // Setup
        UpdateUserDataDTO dto = new UpdateUserDataDTO(
                "João Atualizado",
                "joao.atualizado@example.com",
                null,
                null,
                null
        );
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Test
        DetailUserDataDTO result = userService.updateUserInfo(1L, dto);

        // Assert
        assertThat(result.name()).isEqualTo("João Atualizado");
        assertThat(result.email()).isEqualTo("joao.atualizado@example.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Setup
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Test and Assert
        assertThatThrownBy(() -> userService.updateUserInfo(1L, new UpdateUserDataDTO(
                "João Atualizado",
                "joao.atualizado@example.com",
                null,
                null,
                null
        )))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado");

        verify(userRepository, never()).save(any());
    }
}
