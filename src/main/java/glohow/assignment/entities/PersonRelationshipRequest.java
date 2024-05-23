package glohow.assignment.entities;

import lombok.Data;

import java.sql.Date;

@Data
public class PersonRelationshipRequest {
    private Person person1;
    private String roleNamePerson1;
    private Person person2;
    private String roleNamePerson2;
    private Date relationshipStartDate;
    private String relationshipTypesNamePerson1;
    private String relationshipTypesNamePerson2;
    private String  mainGenealogy;
}
