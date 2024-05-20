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
    private String relationshipTypesName;
    private String  mainGenealogy;
}
