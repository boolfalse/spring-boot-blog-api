package am.github.springbootblogapi.repositories;

import am.github.springbootblogapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByAlias(String alias);

}
