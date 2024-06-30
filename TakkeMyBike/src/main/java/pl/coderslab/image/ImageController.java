package pl.coderslab.image;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class ImageController {

    private static final String IMAGE_DIRECTORY = "/images/";

    @GetMapping("/image/logo")
    public void getImage(HttpServletResponse response) throws IOException {
        String imageName = "img.png";
        File imageFile = new File(IMAGE_DIRECTORY + imageName);
        if (imageFile.exists() && !imageFile.isDirectory()) {
            String contentType;
            if (imageName.toLowerCase().endsWith(".png")) {
                contentType = "image/png";
            } else if (imageName.toLowerCase().endsWith(".jpg") || imageName.toLowerCase().endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else {
                contentType = "application/octet-stream";
            }

            response.setContentType(contentType);
            response.setContentLength((int) imageFile.length());

            try (FileInputStream fis = new FileInputStream(imageFile);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
