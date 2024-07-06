package pl.coderslab.image;

import org.springframework.stereotype.Service;
import pl.coderslab.bike.Bike;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void setImages (Bike bike) {
        List <Image> images = bike.getImages();
        imageRepository.saveAll(images);
    }
}
