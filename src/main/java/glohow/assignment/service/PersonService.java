package glohow.assignment.service;

import glohow.assignment.dao.*;
import glohow.assignment.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private FamiliesRepository familiesRepository;

    public void addPersonAndUpdateRelationship(Person person1, String roleNamePerson1, Person person2, String roleNamePerson2, Date relationshipStartDate, String relationshipTypesName, String mainGenealogyPerson2) {


        Person existingPerson1 = personRepository.findById(person1.getPersonID()).orElseThrow(() -> new RuntimeException("Person1 not found"));

        Roles rolePerson1 = rolesRepository.findByRoleDescription(roleNamePerson1);

        Roles rolePerson2 = rolesRepository.findByRoleDescription(roleNamePerson2);

        RelationshipTypes relationshipTypes = relationshipTypesRepository.findByRelationshipTypeDescription(relationshipTypesName);

        //update relationship1 for person1
        Relationships relationships1 = setRelationshipToPerson2(existingPerson1, rolePerson1, person2, rolePerson2, relationshipTypes, relationshipStartDate);
        //update relationship2 for person2
        Relationships relationships2 = setRelationshipToPerson1(existingPerson1, rolePerson1, person2, rolePerson2, relationshipTypes, relationshipStartDate);

        Relationships savedRelationship1 = relationshipsRepository.save(relationships1);
        Relationships savedRelationship2 = relationshipsRepository.save(relationships2);

        if (existingPerson1.getListRelationshipsPerson01() == null) {
            existingPerson1.setListRelationshipsPerson01(new ArrayList<>());
        }
        existingPerson1.getListRelationshipsPerson01().add(savedRelationship1);

        personRepository.save(existingPerson1);

        Person existingPerson2 = personRepository.save(person2);

        if (existingPerson2.getListRelationshipsPerson02() == null) {
            existingPerson2.setListRelationshipsPerson02(new ArrayList<>());
        }
        existingPerson2.getListRelationshipsPerson02().add(savedRelationship2);

        //Adding heading Families for children
        if (rolePerson1.getRoleDescription().equals("Father") || rolePerson1.getRoleDescription().equals("Mother")) {

            addingHeadFamilies(existingPerson2);

            //Update relationship with sibling
            List<Person> children = personRepository.findPerson02ByPerson01IdAndRelationshipTypeDescription(existingPerson1.getPersonID(), "Parent/Child");
            if (!children.isEmpty()) {
                RelationshipTypes relationshipTypesSibling = relationshipTypesRepository.findByRelationshipTypeDescription("Sibling");
                children.forEach(child -> {
                    if (!(existingPerson2.getPersonID() == child.getPersonID())) {
                        Person existPersonSibling = personRepository.findById(child.getPersonID()).orElseThrow(() -> new RuntimeException("Person not found"));
                        Roles rolesSibling1 = new Roles();
                        Roles rolesSibling2 = new Roles();

                        if (existingPerson2.getGender().equals("0")) {
                            rolesSibling1 = rolesRepository.findByRoleDescription("Brother");
                        } else {
                            rolesSibling1 = rolesRepository.findByRoleDescription("Sister");
                        }
                        if (child.getGender().equals("0")) {
                            rolesSibling2 = rolesRepository.findByRoleDescription("Brother");
                        } else {
                            rolesSibling2 = rolesRepository.findByRoleDescription("Sister");
                        }
                        Date setDate;
                        if((existingPerson2.getDoB().compareTo(child.getDoB()))> 0){
                            setDate = child.getDoB();
                        }else{
                            setDate = existingPerson2.getDoB();
                        }

                        Relationships relationshipsSibling1 = setRelationshipToPerson2(existingPerson2, rolesSibling1, child, rolesSibling2, relationshipTypesSibling, setDate);
                        Relationships relationshipsSibling2 = setRelationshipToPerson1(existingPerson2, rolesSibling1, child, rolesSibling2, relationshipTypesSibling, setDate);

                        Relationships saveRelationshipsSibling1 = relationshipsRepository.save(relationshipsSibling1);
                        Relationships saveRelationshipsSibling2 = relationshipsRepository.save(relationshipsSibling2);
                    }

                });
            }
            //Update relationship with Parents
            List<Person> spousePerson = personRepository.findPerson02ByPerson01IdAndRelationshipTypeDescription(existingPerson1.getPersonID(), "Married");
            if (!spousePerson.isEmpty()) {
                RelationshipTypes relationshipTypesParentChild = relationshipTypesRepository.findByRelationshipTypeDescription("Parent/Child");
                spousePerson.forEach(spouse -> {

                    Person existSpouse = personRepository.findById(spouse.getPersonID()).orElseThrow(() -> new RuntimeException("Person not found"));
                    Roles rolesSpouse = new Roles();
                    Roles rolesChildren = new Roles();

                    boolean check = (existSpouse.getGender().equals("0"));
                    System.out.println(check);
                    System.out.println(existSpouse.getGender());
                    if (existSpouse.getGender().equals("0")) {
                        rolesSpouse = rolesRepository.findByRoleDescription("Father");
                    } else {
                        rolesSpouse = rolesRepository.findByRoleDescription("Mother");
                    }
                    if (existingPerson2.getGender().equals("0")) {
                        rolesChildren = rolesRepository.findByRoleDescription("Son");
                    } else {
                        rolesChildren = rolesRepository.findByRoleDescription("Daughter");
                    }

                    Relationships relationshipsParentChild1 = setRelationshipToPerson2(existSpouse, rolesSpouse, existingPerson2, rolesChildren, relationshipTypesParentChild,existingPerson2.getDoB());
                    Relationships relationshipsParentChild2 = setRelationshipToPerson1(existSpouse, rolesSpouse, existingPerson2, rolesChildren, relationshipTypesParentChild,existingPerson2.getDoB());


                    Relationships saveRelationshipsSibling1 = relationshipsRepository.save(relationshipsParentChild1);
                    Relationships saveRelationshipsSibling2 = relationshipsRepository.save(relationshipsParentChild2);
                    System.out.println("vo toi day chua ahihi");
                });
            }

        }


        //Update relationship Husband and Wife from children
        if (rolePerson2.getRoleDescription().equals("Father") || rolePerson2.getRoleDescription().equals("Mother")) {

            if (rolePerson2.getRoleDescription().equals("Father")) {

                List<Person> listPerson = new ArrayList<>();

                if (rolePerson1.getRoleDescription().equals("Son")) {
                    listPerson = personRepository.findPerson02ByPerson01IdAndRolesPerson01AndRolesPerson02(existingPerson1.getPersonID(), "Son", "Mother");
                } else {
                    listPerson = personRepository.findPerson02ByPerson01IdAndRolesPerson01AndRolesPerson02(existingPerson1.getPersonID(), "Daughter", "Mother");
                }

                if (!listPerson.isEmpty()) {
                    Person existingPerson3 = personRepository.findById(listPerson.get(0).getPersonID()).orElseThrow(() -> new RuntimeException("Person1 not found"));

                    Roles rolePersonHusband = rolesRepository.findByRoleDescription("Husband");
                    Roles rolePersonWife = rolesRepository.findByRoleDescription("Wife");
                    RelationshipTypes relationshipTypes1 = relationshipTypesRepository.findByRelationshipTypeDescription("Married");

                    Relationships relationships3 = new Relationships();
                    relationships3.setPerson01(existingPerson2);
                    relationships3.setPerson02(existingPerson3);
                    relationships3.setPerson01Role(rolePersonHusband);
                    relationships3.setPerson02Role(rolePersonWife);
                    relationships3.setRelationshipTypes(relationshipTypes1);

                    Relationships relationships4 = new Relationships();
                    relationships4.setPerson01(existingPerson3);
                    relationships4.setPerson02(existingPerson2);
                    relationships4.setPerson01Role(rolePersonWife);
                    relationships4.setPerson02Role(rolePersonHusband);
                    relationships4.setRelationshipTypes(relationshipTypes1);

                    Relationships savedRelationship3 = relationshipsRepository.save(relationships3);
                    Relationships savedRelationship4 = relationshipsRepository.save(relationships4);
                }
            }

            if (rolePerson2.getRoleDescription().equals("Mother")) {

                List<Person> listPerson = new ArrayList<>();

                if (rolePerson1.getRoleDescription().equals("Son")) {
                    listPerson = personRepository.findPerson02ByPerson01IdAndRolesPerson01AndRolesPerson02(existingPerson1.getPersonID(), "Son", "Father");
                } else {
                    listPerson = personRepository.findPerson02ByPerson01IdAndRolesPerson01AndRolesPerson02(existingPerson1.getPersonID(), "Daughter", "Father");
                }

                if (!listPerson.isEmpty()) {
                    Person existingPerson3 = personRepository.findById(listPerson.get(0).getPersonID()).orElseThrow(() -> new RuntimeException("Person1 not found"));

                    Roles rolePersonHusband = rolesRepository.findByRoleDescription("Husband");
                    Roles rolePersonWife = rolesRepository.findByRoleDescription("Wife");
                    RelationshipTypes relationshipTypes1 = relationshipTypesRepository.findByRelationshipTypeDescription("Married");

                    Relationships relationships3 = new Relationships();
                    relationships3.setPerson01(existingPerson2);
                    relationships3.setPerson02(existingPerson3);
                    relationships3.setPerson01Role(rolePersonWife);
                    relationships3.setPerson02Role(rolePersonHusband);
                    relationships3.setRelationshipTypes(relationshipTypes1);

                    Relationships relationships4 = new Relationships();
                    relationships4.setPerson01(existingPerson3);
                    relationships4.setPerson02(existingPerson2);
                    relationships4.setPerson01Role(rolePersonHusband);
                    relationships4.setPerson02Role(rolePersonWife);
                    relationships4.setRelationshipTypes(relationshipTypes1);

                    Relationships savedRelationship3 = relationshipsRepository.save(relationships3);
                    Relationships savedRelationship4 = relationshipsRepository.save(relationships4);
                }
            }

            System.out.println("vo toi day chua 0");

            if (mainGenealogyPerson2.equals("1")) {
                System.out.println("vo toi day chua 1");
                List<Person> listPerson = new ArrayList<>();
                String text = existingPerson2.getGender() + "";
                System.out.println("text: " + text);
                if (text.equals("0")) {
                    System.out.println("kiem tra 1");
                    listPerson = personRepository.findPerson02ByPerson01IdAndRoles(existingPerson2.getPersonID(), "Wife");
                } else {
                    System.out.println("Kiem tra 2");
                    listPerson = personRepository.findPerson02ByPerson01IdAndRoles(existingPerson2.getPersonID(), "Husband");
                }
                if (listPerson.isEmpty()) {
                    System.out.println("Kiem tra 3");
                    addingHeadFamilies(existingPerson2);
                } else {
                    System.out.println("kiem tra 4");
                    Person existPerson3 = listPerson.get(0);
                    boolean checkingExistPerson3 = familiesRepository.existsById(existPerson3.getPersonID());
                    if (!checkingExistPerson3) {
                        addingHeadFamilies(existingPerson2);
                    }
                }
            }
        }

        //Check adding Head Families and update relationship
        if (rolePerson2.getRoleDescription().equals("Husband") || rolePerson2.getRoleDescription().equals("Wife")) {
            //checking adding Head Families
            boolean checkingExistHeadFamily = familiesRepository.existsByPersonHeadFamily(existingPerson1);
            if (!checkingExistHeadFamily) {
                addingHeadFamilies(existingPerson2);
            }
            //Update relationship

            List<Person> children = new ArrayList<>();

            List<Person> childrenMother = personRepository.findPerson02ByPerson01IdAndRoles(existingPerson1.getPersonID(), "Mother");
            List<Person> childrenFather = personRepository.findPerson02ByPerson01IdAndRoles(existingPerson1.getPersonID(), "Father");

            if(childrenMother.isEmpty()){
                children = childrenFather;
            }else{
                children = childrenMother;
            }
            if (!children.isEmpty()) {
                RelationshipTypes relationshipTypesParentChild = relationshipTypesRepository.findByRelationshipTypeDescription("Parent/Child");
                children.forEach(child -> {
                    Person existPersonChild = personRepository.findById(child.getPersonID()).orElseThrow(() -> new RuntimeException("Person not found"));
                    Roles rolesChild = new Roles();
                    Roles rolesParents = new Roles();
                    if (rolePerson2.getRoleDescription().equals("Husband")) {
                        rolesParents = rolesRepository.findByRoleDescription("Father");
                    } else {
                        rolesParents = rolesRepository.findByRoleDescription("Mother");
                    }
                    if (child.getGender().equals("0")) {
                        rolesChild = rolesRepository.findByRoleDescription("Son");
                    } else {
                        rolesChild = rolesRepository.findByRoleDescription("Daughter");
                    }

                    Relationships relationshipsParentToChild = setRelationshipToPerson2(existingPerson2, rolesParents, existPersonChild, rolesChild, relationshipTypesParentChild, existPersonChild.getDoB());
                    Relationships relationshipsChildToParent = setRelationshipToPerson1(existingPerson2, rolesParents, existPersonChild, rolesChild, relationshipTypesParentChild, existPersonChild.getDoB());

                    Relationships saveRelationshipsParentToChild = relationshipsRepository.save(relationshipsParentToChild);
                    Relationships saveRelationshipsChildToParent = relationshipsRepository.save(relationshipsChildToParent);

                });
            }
        }


    }

    public Relationships setRelationshipToPerson2(Person person1, Roles rolePerson1, Person person2, Roles rolePerson2, RelationshipTypes relationshipTypes, Date relationshipStartDate) {
        Relationships relationships1 = new Relationships();
        relationships1.setPerson01(person1);
        relationships1.setPerson02(person2);
        relationships1.setPerson01Role(rolePerson1);
        relationships1.setPerson02Role(rolePerson2);
        relationships1.setRelationshipTypes(relationshipTypes);
        relationships1.setDateOfRelationshipStarted(relationshipStartDate);
        return relationships1;
    }

    public Relationships setRelationshipToPerson1(Person person1, Roles rolePerson1, Person person2, Roles rolePerson2, RelationshipTypes relationshipTypes, Date relationshipStartDate) {
        Relationships relationships1 = new Relationships();
        relationships1.setPerson01(person2);
        relationships1.setPerson02(person1);
        relationships1.setPerson01Role(rolePerson2);
        relationships1.setPerson02Role(rolePerson1);
        relationships1.setRelationshipTypes(relationshipTypes);
        relationships1.setDateOfRelationshipStarted(relationshipStartDate);
        return relationships1;
    }


    public void addPersonAndMainGenealogy(Person person, String mainGenealogy) {
        if (mainGenealogy.equals("1")) {
            addingHeadFamilies(person);
        }
        personRepository.save(person);
    }

    public void updatePerson(Person person) {
        Person existPerson = personRepository.findById(person.getPersonID()).orElseThrow(() -> new RuntimeException("Person Update not found"));
                existPerson = person;
        if(person.getMaritalStatus().equals("Divorced")){
           List<Person>  listPerson = personRepository.findPerson02ByPerson01IdAndRelationshipTypeDescription(person.getPersonID(), "Married");
            Person existPersonSpouse = personRepository.findById(listPerson.get(0).getPersonID()).orElseThrow(() -> new RuntimeException("Person not found"));
            existPersonSpouse.setMaritalStatus("Divorced");
            personRepository.save(existPersonSpouse);
        }
        personRepository.save(existPerson);
    }

    public void addingHeadFamilies(Person person) {
        Families families = new Families();
        families.setFamilyName(person.getFirstName());
        families.setPersonHeadFamily(person);
        Families saveFamilies = familiesRepository.save(families);
    }

    public boolean isHeadingFamily(int personID) {

        try {
            Person existingPerson1 = personRepository.findById(personID).orElseThrow(() -> new RuntimeException("Person1 not found"));
            return familiesRepository.existsByPersonHeadFamily(existingPerson1);
        } catch (Exception e) {
            return false;
        }
    }
}
