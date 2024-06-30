package pl.coderslab.bike;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.address.Address;
import pl.coderslab.address.AddressService;
import pl.coderslab.user.User;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class BikeService {
    private final BikeRepository bikeRepository;
    private final AddressService addressService;

    public BikeService(BikeRepository bikeRepository, AddressService addressService) {
        this.bikeRepository = bikeRepository;
        this.addressService = addressService;
    }

    public void save(Bike bike) throws IOException {

        Address address = bike.getAddress();

        addressService.save(address);


        bikeRepository.save(bike);
    }

    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    public List<Bike> findAllByOwner(User user) {
        return bikeRepository.findAllByOwner(user);
    }

    public Optional<Bike> findById(Long id) {
        return bikeRepository.findById(id);
    }
}
