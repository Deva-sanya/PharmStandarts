package myProject.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Standard")
public class Standard {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person chemist;

    @Column(name = "name")
    @NotEmpty(message = "Name of standard should not be empty")
    @Size(min = 2, max = 100, message = "The name of the standard should be between two and 100 characters long.")
    private String name;

    @Column(name = "catNumber")
    @NotEmpty(message = "Catalog number of standard should not be empty")
    @Size(min = 2, max = 30, message = "The catalog number of the standard should be between two and 30 characters long.")
    private String catNumber;

    @Column(name = "expired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expired;

    @Column(name = "add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date add;

    @Column(name = "weight")
    @NotNull
    @Positive
    private Double weight;

    @Column(name = "vials")
    @NotNull
    @Positive
    private Integer vials;

    public Standard() {

    }
}
