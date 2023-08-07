package am.github.springbootblogapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"title"}) }
)
public class Post {
    // primary field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // additional fields
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

    // relations
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>(); // (instead of List we use Set for not having duplicates)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}
