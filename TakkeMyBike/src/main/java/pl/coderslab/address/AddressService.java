package pl.coderslab.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

@Service
@Transactional

public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    public void save (Address address) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + googleMapsApiAddressQuery(address) + "&key=AIzaSyBVEnKq5YxoW7wOQRCj_smmVYfgiIpfK0w");
        JsonNode jsonNode = mapper.readTree(url);
        JsonNode node = jsonNode.get("results").get(0);
        address.setLatitude(node.get("geometry").get("location").get("lat").asDouble());
        address.setLongitude(node.get("geometry").get("location").get("lng").asDouble());
        JsonNode addressComponents = node.get("address_components");
        int i = 0;
        while (addressComponents.get(i)!=null){
            int j = 0;
            while (addressComponents.get(i).get("types").get(j)!=null){
                String type = addressComponents.get(i).get("types").get(j).toString().replaceAll("\"", "");
                if (type.equals("administrative_area_level_1")) {
                    address.setAdministrative_area_level_1(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("administrative_area_level_2")) {
                    address.setAdministrative_area_level_2(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("country")) {
                    address.setCountry(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("sublocality_level_1  ")) {
                    address.setDistrict(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("subpremise")) {
                    address.setApartmentNumber(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("street_number")) {
                    address.setStreetNumber(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("route")) {
                    address.setStreet(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("postal_code")) {
                    address.setPostalCode(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                if (type.equals("locality")) {
                    address.setCity(addressComponents.get(i).get("long_name").toString().replaceAll("\"", ""));
                }
                j++;
            }
            i++;
        }
        addressRepository.save(address);
    }
    public static String googleMapsApiAddressQuery(Address address) throws UnsupportedEncodingException {
        StringBuilder formattedAddress = new StringBuilder();

        if (address.getStreet() != null && !address.getStreet().isEmpty()) {
            formattedAddress.append(URLEncoder.encode(address.getStreet(), "UTF-8"));
        }

        if (address.getStreetNumber() != null && !address.getStreetNumber().isEmpty()) {
            formattedAddress.append("%20")
                    .append(URLEncoder.encode(address.getStreetNumber(), "UTF-8"));
        }

        if (address.getCity() != null && !address.getCity().isEmpty()) {
            formattedAddress.append(",+")
                    .append(URLEncoder.encode(address.getCity(), "UTF-8"));
        }

        if (address.getPostalCode() != null && !address.getPostalCode().isEmpty()) {
            formattedAddress.append(",+")
                    .append(URLEncoder.encode(address.getPostalCode(), "UTF-8"));
        }
        return formattedAddress.toString();
    }
}
