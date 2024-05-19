package glohow.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "relationships")
public class Relationships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_ID")
    private int relationshipID;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "family_id", nullable = false)
    private Families families;

    @Column(name = "date_of_relationship_started")
    private Date dateOfRelationshipStarted;

    @Column(name = "date_of_relationship_ended")
    private Date dateOfRelationshipEnded;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "relationship_types_id", nullable = false)
    private RelationshipTypes relationshipTypes;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "person_id_1", nullable = false)
    private Person person01;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "person_id_2", nullable = false)
    private Person person02;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "role_01_id", nullable = false)
    private Roles person01Role;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "role_02_id", nullable = false)
    private Roles person02Role;

}
