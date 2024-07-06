package pl.coderslab.bike;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.coderslab.address.Address;
import pl.coderslab.image.Image;
import pl.coderslab.rent.Rent;
import pl.coderslab.user.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Lob
    private byte[] image;

    @Column(columnDefinition = "LONGTEXT")
    private String base64Image;

    @OneToMany(mappedBy = "bike")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "bike")
    private List<Rent> rents = new ArrayList<>();

}
