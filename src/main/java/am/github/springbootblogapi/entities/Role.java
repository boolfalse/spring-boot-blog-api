package am.github.springbootblogapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    // primary field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // additional fields
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "alias", nullable = false, unique = true)
    private String alias;
}
