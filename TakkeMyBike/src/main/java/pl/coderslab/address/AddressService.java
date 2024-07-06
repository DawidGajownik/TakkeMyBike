package pl.coderslab.address;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.coderslab.bike.Bike;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional

public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    public Address findData (Address address) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + googleMapsApiAddressQuery(address) + "&key=AIzaSyBVEnKq5YxoW7wOQRCj_smmVYfgiIpfK0w");
        return getAddressData(address, mapper, url);
    }
    public Address findDataFromString (String addressString) throws IOException {
        if (addressString==null||addressString.isEmpty()) {
            return null;
        }
        Address address = new Address();
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + addressString.replaceAll(" ", "%20") + "&key=AIzaSyBVEnKq5YxoW7wOQRCj_smmVYfgiIpfK0w");
        return getAddressData(address, mapper, url);
    }

    private Address getAddressData(Address address, ObjectMapper mapper, URL url) throws IOException {
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
        return address;
    }

    public void save (Address address) throws IOException {
        addressRepository.save(findData(address));
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
    private Double haversine (Double lat1, Double lon1, Double lat2, Double lon2) {
        if (lat1==null || lat2==null) {
            return null;
        }
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.round(6371.0 * c * 100.0) / 100.0;
    }
    public Map<Bike, Double> distanceCalculate (List<Bike> bikes, Address address) throws IOException {
        Map<Bike,Double> bikesWithDistance = new LinkedHashMap<>();
        Double addressLatitude = null;
        Double addressLongitude = null;
        if (address!=null) {
            addressLatitude = findData(address).getLatitude();
            addressLongitude = findData(address).getLongitude();
        }
        for (Bike bike : bikes) {
            Double bikeLatitude = bike.getAddress().getLatitude();
            Double bikeLongitude = bike.getAddress().getLongitude();
            bikesWithDistance.put(bike, haversine(addressLatitude, addressLongitude, bikeLatitude, bikeLongitude));
        }
        return bikesWithDistance;
    }

}
