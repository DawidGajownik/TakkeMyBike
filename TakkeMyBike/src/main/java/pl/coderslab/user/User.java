package pl.coderslab.user;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.NumberFormat;
import pl.coderslab.bike.Bike;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String login;

    private String description;

    @Email
    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull

    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String telephoneNumber;

    private boolean rentToOthers;

    private boolean rentFromOthers;

    @Min(0)
    @Max(2)
    private int level;

    @OneToMany(mappedBy = "owner")
    private Set<Bike> bikes = new HashSet<>();

    private boolean hasReservationNotifications;
    private boolean hasRentNotifications;
    private boolean hasMessageNotification;

}
