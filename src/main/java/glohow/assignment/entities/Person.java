package glohow.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private char gender;

    @Column(name = "date_of_birth")
    private Date doB;

    @Column(name = "date_of_die")
    private Date doD;

    @Column(name = "address")
    private String address;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "marital_status_id")
    private MaritalStatus maritalStatus;

    @OneToMany(mappedBy = "person01", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Relationships> listRelationshipsPerson01;

    @OneToMany(mappedBy = "person02", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Relationships> listRelationshipsPerson02;


}
