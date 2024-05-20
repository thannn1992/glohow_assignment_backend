package glohow.assignment.dao;

import glohow.assignment.entities.RelationshipTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "relationship_types")
public interface RelationshipTypesRepository extends JpaRepository<RelationshipTypes, Integer> {
    RelationshipTypes findByRelationshipTypeDescription(String relationshipTypeDescription);

}
