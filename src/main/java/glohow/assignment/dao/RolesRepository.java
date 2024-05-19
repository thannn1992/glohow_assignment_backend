package glohow.assignment.dao;

import glohow.assignment.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "roles")
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByRoleDescription(String roleDescription);
}
