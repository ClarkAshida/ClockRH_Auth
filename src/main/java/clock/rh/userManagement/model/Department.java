package clock.rh.userManagement.model;

import clock.rh.userManagement.dto.department.CreateNewDepartmentDataDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "department")
@Entity(name = "Department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<User> users; // One department can have many users

    @OneToMany
    @JoinColumn(name = "department_id")
    private List<User> managers;  // One department can have many managers

    public Department(String name, ArrayList<User> users, ArrayList<User> managers) {
        this.name = name;
        this.users = users;
        this.managers = managers;
    }
}
