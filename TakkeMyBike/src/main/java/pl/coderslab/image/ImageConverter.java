package pl.coderslab.image;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageConverter implements Converter <MultipartFile, Image> {
    @Override
    public Image convert(MultipartFile source) {
        Image image = new Image();
        try {
            image.setImage(source.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
