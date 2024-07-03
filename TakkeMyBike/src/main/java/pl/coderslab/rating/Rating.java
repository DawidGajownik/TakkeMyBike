package pl.coderslab.rating;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.rent.Rent;
import pl.coderslab.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Getter
@Setter

public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private int rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "rated_id")
    private User rated;

    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;

    private LocalDate date;
}
