package pl.coderslab.image;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.bike.Bike;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    @Column(columnDefinition = "LONGTEXT")
    private String base64Image;

    @ManyToOne
    private Bike bike;
}
