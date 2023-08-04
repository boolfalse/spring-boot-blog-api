package am.github.springbootblogapi.repositories;

import am.github.springbootblogapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //
}
