package pl.coderslab.bike;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.coderslab.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Size(max = 30)
    private String title;

    @NonNull
    @Size(min = 15)
    private String description;

    @NonNull
    private double pricePerDay;

    @Min(1)
    @NonNull
    private int minRentDays;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Lob
    private byte[] image;

    private String address;
    private String city;
    private String postalCode;
    private double latitude;
    private double longitude;


}
