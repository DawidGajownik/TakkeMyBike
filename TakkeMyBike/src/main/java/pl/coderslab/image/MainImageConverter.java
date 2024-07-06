package pl.coderslab.image;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MainImageConverter implements Converter <MultipartFile, byte[]> {
    @Override
    public byte [] convert(MultipartFile source) {
        try {
            return source.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
