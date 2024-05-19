package glohow.assignment.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "marital_status")
public class MaritalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marital_status_id")
    private int maritalStatusID;

    @Column(name = "marital_status_description")
    private String maritalStatusDescription;


}
