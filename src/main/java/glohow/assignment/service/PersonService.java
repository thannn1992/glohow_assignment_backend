package glohow.assignment.service;

import glohow.assignment.dao.PersonRepository;
import glohow.assignment.dao.RelationshipTypesRepository;
import glohow.assignment.dao.RelationshipsRepository;
import glohow.assignment.dao.RolesRepository;
import glohow.assignment.entities.Person;
import glohow.assignment.entities.RelationshipTypes;
import glohow.assignment.entities.Relationships;
import glohow.assignment.entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RelationshipsRepository relationshipsRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RelationshipTypesRepository relationshipTypesRepository;
public void addPersonAndUpdateRelationshipFather(Person person1, String roleNamePerson1, Person person2, String roleNamePerson2, Date relationshipStartDate, String relationshipTypesName){

    Person existingPerson1 = personRepository.findById(person1.getPersonID()).orElseThrow(()-> new RuntimeException("Person1 not found"));

    Roles rolePerson1 = rolesRepository.findByRoleDescription(roleNamePerson1);

    Roles rolePerson2 = rolesRepository.findByRoleDescription(roleNamePerson2);

    RelationshipTypes relationshipTypes = relationshipTypesRepository.findByRelationshipTypeDescription(relationshipTypesName);

    //update relationship1 for person1
    Relationships relationships1 = new Relationships();
    relationships1.setPerson01(existingPerson1);
    relationships1.setPerson02(person2);
    relationships1.setPerson01Role(rolePerson1);
    relationships1.setPerson02Role(rolePerson2);
    relationships1.setDateOfRelationshipStarted(relationshipStartDate);
    relationships1.setRelationshipTypes(relationshipTypes);

    //update relationship2 for person2
    Relationships relationships2 = new Relationships();
    relationships2.setPerson02(existingPerson1);
    relationships2.setPerson01(person2);
    relationships2.setPerson01Role(rolePerson2);
    relationships2.setPerson02Role(rolePerson1);
    relationships2.setDateOfRelationshipStarted(relationshipStartDate);
    relationships2.setRelationshipTypes(relationshipTypes);

    Relationships savedRelationship1 = relationshipsRepository.save(relationships1);
    Relationships savedRelationship2 = relationshipsRepository.save(relationships2);

    if(existingPerson1.getListRelationshipsPerson01() == null){
        person1.setListRelationshipsPerson01(new ArrayList<>());
    }
    existingPerson1.getListRelationshipsPerson01().add(savedRelationship1);

    person2.getListRelationshipsPerson02().add(savedRelationship2);

    personRepository.save(person1);

    personRepository.save(person2);
}
}
