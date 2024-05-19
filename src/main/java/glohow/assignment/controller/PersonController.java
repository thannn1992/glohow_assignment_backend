package glohow.assignment.controller;

import glohow.assignment.entities.Person;
import glohow.assignment.entities.PersonRelationshipRequest;
import glohow.assignment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping("/add")
    public ResponseEntity<?> addPerson(@RequestBody PersonRelationshipRequest request) {
        System.out.println("role: " + request.getRoleNamePerson1());
        Person person1 = request.getPerson1();
        String roleNamePerson1 = request.getRoleNamePerson1();
        Person person2 = request.getPerson2();
        String roleNamePerson2 = request.getRoleNamePerson2();
        Date relationshipStartDate = request.getRelationshipStartDate();
        String relationshipTypesName = request.getRelationshipTypesName();
        personService.addPersonAndUpdateRelationshipFather(person1, roleNamePerson1, person2, roleNamePerson2, relationshipStartDate, relationshipTypesName);
        return ResponseEntity.ok("Adding new Person successfully!");
    }

}
