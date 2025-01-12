package clock.rh.userManagement.model;

import clock.rh.userManagement.dto.users.CreateNewUserDataDTO;
import clock.rh.userManagement.dto.users.UpdateUserDataDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import java.time.LocalDateTime;

@Entity(name = "Users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Personal User Information
    private String name;
    private String email;
    private String password;
    private String cpf;
    private LocalDateTime admissionDate;

    // Department Information
    @ManyToOne
    @JoinColumn(name = "department_id") // One user can have only one department
    private Department department;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private boolean active;

    // Constructor for creating a new user information
    public User(CreateNewUserDataDTO newUserDataDTO, Department department) {
        this.name = newUserDataDTO.name();
        this.email = newUserDataDTO.email();
        this.password = newUserDataDTO.password();
        this.cpf = newUserDataDTO.cpf();
        this.admissionDate = newUserDataDTO.admissionDate();
        this.role = newUserDataDTO.role();
        this.department = department;
        this.active = true;
    }

    public void deactivateUser() {
        this.active = false;
    }

    public boolean isManager() {
        return this.role == UserRole.GESTOR;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.role.name());
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.admissionDate.isBefore(LocalDateTime.now().minusYears(1));
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
