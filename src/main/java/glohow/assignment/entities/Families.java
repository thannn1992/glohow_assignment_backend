package glohow.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "families")
public class Families {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "family_id")
    private int familyID;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "date_of_family_from")
    private Date dateOfFamilyFrom;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "person_id")
    private Person personHeadFamily;
}
