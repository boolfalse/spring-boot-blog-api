package am.github.springbootblogapi.repositories;

import am.github.springbootblogapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    //
}
