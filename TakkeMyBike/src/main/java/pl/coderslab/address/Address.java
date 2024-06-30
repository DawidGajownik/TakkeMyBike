package pl.coderslab.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String streetNumber;
    private String apartmentNumber;
    private String district;
    private String city;
    private String administrative_area_level_2;
    private String administrative_area_level_1;
    private String country;
    private String postalCode;
    private double latitude;
    private double longitude;

}
