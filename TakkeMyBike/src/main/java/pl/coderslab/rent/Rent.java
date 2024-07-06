package pl.coderslab.rent;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.bike.Bike;
import pl.coderslab.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name ="owner_id", nullable = false)
    private User owner;

    @ManyToOne
    private Bike bike;
    private int status; // 0 = requested 1 = canceled by owner 2 = confirmed 3 = canceled by customer
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
}