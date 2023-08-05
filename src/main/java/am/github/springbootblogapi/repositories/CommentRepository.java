package am.github.springbootblogapi.repositories;

import am.github.springbootblogapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // this is like an accessor
    // so the JpaRepository will automatically split the method name like:
    // findCommentsByPostId = find (native) + comments (collection) + postId (property)
    List<Comment> findCommentsByPostId(Long postId);
}
