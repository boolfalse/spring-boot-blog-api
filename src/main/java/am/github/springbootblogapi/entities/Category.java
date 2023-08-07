package am.github.springbootblogapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    // primary field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // additional fields
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = true)
    private String description;

    // relations
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Post> posts;
}
