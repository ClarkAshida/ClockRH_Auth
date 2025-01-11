package clock.rh.userManagement.model;

import jakarta.persistence.*;
import lombok.*;

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
}
