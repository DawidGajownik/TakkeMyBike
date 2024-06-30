package pl.coderslab.bike;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }
    public void save(Bike bike) {
        String address = bike.getAddress() + ", " + bike.getCity() + ", " + bike.getPostalCode();
        double[] latLong = getLatLongFromAddress(address);
        bike.setLatitude(latLong[0]);
        bike.setLongitude(latLong[1]);
        bikeRepository.save(bike);
    }
    public List<Bike> findAll(){
        return bikeRepository.findAll();
    }
    public List<Bike> findAllByOwner(User user){
        return bikeRepository.findAllByOwner(user);
    }
    public Optional<Bike> findById(Long id){
        return bikeRepository.findById(id);
    }
    private double[] getLatLongFromAddress(String address) {
        // Skorzystaj z RestTemplate do wykonania żądania do Google Geocoding API
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=YOUR_API_KEY";
        GeocodeResponse response = restTemplate.getForObject(url, GeocodeResponse.class);

        if (response != null && response.getResults().length > 0) {
            double lat = response.getResults()[0].getGeometry().getLocation().getLat();
            double lng = response.getResults()[0].getGeometry().getLocation().getLng();
            return new double[]{lat, lng};
        }
        throw new RuntimeException("Unable to fetch coordinates");
    }
    class GeocodeResponse {
        private GeocodeResult[] results;

        // Gettery i settery

        public GeocodeResult[] getResults() {
            return results;
        }

        public void setResults(GeocodeResult[] results) {
            this.results = results;
        }
    }

    class GeocodeResult {
        private GeocodeGeometry geometry;

        // Gettery i settery

        public GeocodeGeometry getGeometry() {
            return geometry;
        }

        public void setGeometry(GeocodeGeometry geometry) {
            this.geometry = geometry;
        }
    }

    class GeocodeGeometry {
        private GeocodeLocation location;

        // Gettery i settery

        public GeocodeLocation getLocation() {
            return location;
        }

        public void setLocation(GeocodeLocation location) {
            this.location = location;
        }
    }

    class GeocodeLocation {
        private double lat;
        private double lng;

        // Gettery i settery

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
}
}
