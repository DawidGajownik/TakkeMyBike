package pl.coderslab.image;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ImageConverter implements Converter <MultipartFile, byte []> {
    @Override
    public byte [] convert(MultipartFile source) {
        try {
            return source.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
