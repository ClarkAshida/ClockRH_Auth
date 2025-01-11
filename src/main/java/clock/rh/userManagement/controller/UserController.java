package clock.rh.userManagement.controller;

import clock.rh.userManagement.dto.users.CreateNewUserDataDTO;
import clock.rh.userManagement.dto.users.DetailUserDataDTO;
import clock.rh.userManagement.dto.users.ListUserDataDTO;
import clock.rh.userManagement.model.User;
import clock.rh.userManagement.repository.DepartmentRepository;
import clock.rh.userManagement.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailUserDataDTO> createUser(
            @RequestBody @Valid CreateNewUserDataDTO createNewUserDataDTO,
            UriComponentsBuilder uriBuilder) {

        var department = departmentRepository.findById(createNewUserDataDTO.departmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        var user = new User(createNewUserDataDTO, department);
        userRepository.save(user);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetailUserDataDTO(user));
    }

    @GetMapping
    public ResponseEntity<Page<ListUserDataDTO>> GetAllUsers(@PageableDefault(size = 10, sort = {"name"} ) Pageable pagination) {
        var page = userRepository.findAllByActiveTrue(pagination).map(ListUserDataDTO::new);
        return ResponseEntity.ok(page);
    }

}
