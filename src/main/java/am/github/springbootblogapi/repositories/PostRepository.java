package am.github.springbootblogapi.repositories;

import am.github.springbootblogapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByCategoryId(int categoryId);

}
