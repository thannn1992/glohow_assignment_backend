package glohow.assignment.dao;

import glohow.assignment.entities.Relationships;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "relationships")
public interface RelationshipsRepository extends JpaRepository<Relationships, Integer> {

}
