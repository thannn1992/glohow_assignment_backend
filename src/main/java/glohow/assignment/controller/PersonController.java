package glohow.assignment.controller;

import glohow.assignment.entities.Person;
import glohow.assignment.entities.PersonRelationshipRequest;
import glohow.assignment.entities.PersonRequest;
import glohow.assignment.entities.RelationshipTypesRequest;
import glohow.assignment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("/add_new_person_relationship")
    public ResponseEntity<?> addPersonRelationship(@RequestBody PersonRelationshipRequest request) {
        Person person1 = request.getPerson1();
        String roleNamePerson1 = request.getRoleNamePerson1();
        Person person2 = request.getPerson2();

        String roleNamePerson2 = request.getRoleNamePerson2();

        Date relationshipStartDate = request.getRelationshipStartDate();
        String relationshipTypesName1 = request.getRelationshipTypesNamePerson1();
        String relationshipTypesName2 = request.getRelationshipTypesNamePerson2();

        String mainGenealogy = request.getMainGenealogy();
        personService.addPersonAndUpdateRelationship(person1, roleNamePerson1, person2, roleNamePerson2, relationshipStartDate, relationshipTypesName1, relationshipTypesName2, mainGenealogy);
        return ResponseEntity.ok("Adding new Person successfully!");
    }
    @PostMapping("/add_new_person")
    public ResponseEntity<?> addNewPerson(@RequestBody PersonRequest request) {
        Person person = request.getPerson();
        String mainGenealogy = request.getMainGenealogy();
        personService.addPersonAndMainGenealogy(person, mainGenealogy);
        return ResponseEntity.ok("Adding new Person successfully!");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePerson(@RequestBody Person request) {

        personService.updatePerson(request);
        return ResponseEntity.ok("Update Person successfully!");
    }

    @PostMapping("/exist_heading_family")
    public ResponseEntity<?> isHeadingFamily(@RequestBody int request) {
       Boolean result =  personService.isHeadingFamily(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/take_date_started_relationship")
    public ResponseEntity<?> takeDateStartedRel(@RequestBody RelationshipTypesRequest relationshipTypesRequest) {
        int personID = relationshipTypesRequest.getPersonID();
        String nameOfRel = relationshipTypesRequest.getNameOfRel();
        String result =  personService.takeDateOfStartedRel(personID, nameOfRel);
        return ResponseEntity.ok(result);
    }

}
