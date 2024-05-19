package glohow.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "relationship_types")
public class RelationshipTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relationship_types_id")
    private int relationshipTypeID;

    @Column(name = "relationship_types_description")
    private String relationshipTypeDescription;

}
