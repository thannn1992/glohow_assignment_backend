package glohow.assignment.dao;

import glohow.assignment.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Date;
import java.util.List;

@RepositoryRestResource(path = "person")
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT r.person02 FROM Relationships r WHERE r.person01.personID = :person01Id")
    List<Person> findPerson02ByPerson01Id(@Param("person01Id") Integer person01Id);

    @Query("SELECT DISTINCT r.person02 " +
            "FROM Relationships r " +
            "WHERE r.person01.personID = :person01Id AND r.person01Role.roleDescription = :roleName1")
    List<Person> findPerson02ByPerson01IdAndRoles(@Param("person01Id") Integer person01Id,
                                                  @Param("roleName1") String roleName1);

    @Query("SELECT DISTINCT r.person02 " +
            "FROM Relationships r " +
            "WHERE r.person01.personID = :person01Id AND r.person01Role.roleDescription = :roleName1 AND r.person02Role.roleDescription = :roleName2 ")
    List<Person> findPerson02ByPerson01IdAndRolesPerson01AndRolesPerson02(@Param("person01Id") Integer person01Id,
                                                  @Param("roleName1") String roleName1, @Param("roleName2") String roleName2);

    @Query("SELECT DISTINCT r.person02 " +
            "FROM Relationships r " +
            "WHERE r.person01.personID = :person01Id AND r.relationshipTypes.relationshipTypeDescription = :relationshipTypeDescription")
    List<Person> findPerson02ByPerson01IdAndRelationshipTypeDescription(@Param("person01Id") Integer person01Id,
                                                  @Param("relationshipTypeDescription") String relationshipTypeDescription);
    @Query("SELECT r.dateOfRelationshipStarted FROM Relationships r " +
            "WHERE r.person01.personID = :person01ID AND" +
            " r.relationshipTypes.relationshipTypeDescription = :relTypeDescription")
    String findDateStartedRelationshipByPersonId(@Param("person01ID") Integer person01ID,
                                               @Param("relTypeDescription")String relTypeDescription);
}
