package glohow.assignment.dao;

import glohow.assignment.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "person")
public interface PersonRepository extends JpaRepository<Person, Integer> {

}
