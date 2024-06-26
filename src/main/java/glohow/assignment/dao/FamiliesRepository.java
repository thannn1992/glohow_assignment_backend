package glohow.assignment.dao;

import glohow.assignment.entities.Families;
import glohow.assignment.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "families")
public interface FamiliesRepository extends JpaRepository<Families, Integer> {
public boolean existsByPersonHeadFamily(Person person);

}
