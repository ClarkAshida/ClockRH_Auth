package clock.rh.userManagement.controller;

import clock.rh.userManagement.dto.users.CreateNewUserDataDTO;
import clock.rh.userManagement.dto.users.DetailUserDataDTO;
import clock.rh.userManagement.dto.users.ListUserDataDTO;
import clock.rh.userManagement.dto.users.UpdateUserDataDTO;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import clock.rh.userManagement.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailUserDataDTO> createUser(
            @RequestBody @Valid CreateNewUserDataDTO createNewUserDataDTO,
            UriComponentsBuilder uriBuilder) {

        var department = departmentRepository.findById(createNewUserDataDTO.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Este setor não foi encontrado"));

        String encryptedPassword = passwordEncoder.encode(createNewUserDataDTO.password());

        var user = new User(createNewUserDataDTO, department);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailUserDataDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDataDTO>> GetAllUsers(@PageableDefault(size = 10, sort = {"name"} ) Pageable pagination) {
        var page = userRepository.findAllByActiveTrue(pagination).map(ListUserDataDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailUserDataDTO> GetUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este usuário não foi encontrado"));
        return ResponseEntity.ok(new DetailUserDataDTO(user));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetailUserDataDTO> updateUserInfo(@PathVariable Long id, @RequestBody @Valid UpdateUserDataDTO dto) {
        DetailUserDataDTO updatedUser = userService.updateUserInfo(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Este usuário não foi encontrado"));
        user.deactivateUser();
        return ResponseEntity.noContent().build();
    }

}
