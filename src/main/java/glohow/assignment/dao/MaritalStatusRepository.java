package glohow.assignment.dao;

import glohow.assignment.entities.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "marital_status")
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Integer> {

}
