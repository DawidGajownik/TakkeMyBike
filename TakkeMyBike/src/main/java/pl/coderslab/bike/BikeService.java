package pl.coderslab.bike;


import org.springframework.stereotype.Service;
import pl.coderslab.address.Address;
import pl.coderslab.address.AddressService;
import pl.coderslab.image.Image;
import pl.coderslab.user.User;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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

        Image mainImage = new Image();
        mainImage.setImage(bike.getImage());
        List<Image> allImages = new ArrayList<>(List.of(mainImage));
        allImages.addAll(bike.getImages());
        bike.setImages(allImages.stream().peek(s -> s.setBase64Image(Base64.getEncoder().encodeToString(s.getImage()))).peek(s -> s.setBike(bike)).toList());

        addressService.save(address);
        bikeRepository.save(bike);
    }

    public List<Bike> findAll() {
        return processMultipleBikesImages(bikeRepository.findAll());
    }

    public List<Bike> findAllByOwner(User user) {
        return processMultipleBikesImages(bikeRepository.findAllByOwner(user));
    }

    private List<Bike> processMultipleBikesImages(List<Bike> bikes) {
        for (Bike bike : bikes) {
            processImages(bike);
        }
        return bikes;
    }

    public Optional<Bike> findById(Long id) {
        return Optional.of(processImages(bikeRepository.findById(id).get()));
    }

    public Bike processImages(Bike bike) {
        bike.setImages(bike.getImages().stream().peek(s -> s.setBase64Image(Base64.getEncoder().encodeToString(s.getImage()))).peek(s -> s.setBike(bike)).toList());
        return bike;
    }


    public Map<Bike, Double> findBikesWithFilters(String search, Double minPrice, Double maxPrice, String owner, String address, Integer maxDistance, Integer minRentDays, String startDate, String endDate, String sort) throws IOException {
        LocalDate dateStart = null;
        LocalDate dateEnd = null;
        if (startDate != null && !startDate.isEmpty()) {
            dateStart = LocalDate.parse(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            dateEnd = LocalDate.parse(endDate);
        }
        List<Bike> bikes = bikeRepository.findAllWithFilters(search, minPrice, maxPrice, owner, minRentDays, dateStart, dateEnd);
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("priceAsc")) {
                bikes = bikes.stream().sorted((s1, s2) -> (int) (s1.getPricePerDay() - s2.getPricePerDay())).toList();
            }
            if (sort.equals("priceDesc")) {
                bikes = bikes.stream().sorted((s1, s2) -> (int) (s2.getPricePerDay() - s1.getPricePerDay())).toList();
            }
            if (sort.equals("titleAsc")) {
                bikes = bikes.stream().sorted(Comparator.comparing(Bike::getTitle)).toList();
            }
            if (sort.equals("titleDesc")) {
                bikes = bikes.stream().sorted((s1, s2) -> s2.getTitle().compareTo(s1.getTitle())).toList();
            }
            if (sort.equals("ownerAsc")) {
                bikes = bikes.stream().sorted(Comparator.comparing(s -> s.getOwner().getLogin())).toList();
            }
            if (sort.equals("ownerDesc")) {
                bikes = bikes.stream().sorted((s1, s2) -> s2.getOwner().getLogin().compareTo(s1.getOwner().getLogin())).toList();
            }
            if (sort.equals("minRentDaysAsc")) {
                bikes = bikes.stream().sorted(Comparator.comparingInt(Bike::getMinRentDays)).toList();
            }
            if (sort.equals("minRentDaysDesc")) {
                bikes = bikes.stream().sorted((s1, s2) -> s2.getMinRentDays() - s1.getMinRentDays()).toList();
            }
        }
        Map<Bike, Double> bikesMap = addressService.distanceCalculate(bikes, addressService.findDataFromString(address));
        if (maxDistance != null && address!=null && !address.isEmpty()) {
            bikesMap.values().removeIf(s -> s > maxDistance);
        }
        if (sort != null && !sort.isEmpty()) {
            if (sort.equals("distanceAsc")) {
                List<Map.Entry<Bike,Double>> toSort = new ArrayList<>(bikesMap.entrySet());
                toSort.sort((s1,s2)->{
                    if(s1.getValue()!=null&&s2.getValue()!=null) {
                        return s1.getValue().compareTo(s2.getValue());
                    }
                    return 0;
                });
                Map<Bike,Double> sorted = new LinkedHashMap<>();
                for (Map.Entry<Bike,Double> entry : toSort) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
                bikesMap = sorted;
            }
            if (sort.equals("distanceDesc")) {
                List<Map.Entry<Bike,Double>> toSort = new ArrayList<>(bikesMap.entrySet());
                toSort.sort((s1,s2)->{
                    if(s1.getValue()!=null&&s2.getValue()!=null) {
                        return s2.getValue().compareTo(s1.getValue());
                    }
                    return 0;
                });
                Map<Bike,Double> sorted = new LinkedHashMap<>();
                for (Map.Entry<Bike,Double> entry : toSort) {
                    sorted.put(entry.getKey(), entry.getValue());
                }
                bikesMap = sorted;
            }
        }
        return bikesMap;
    }
    public Map <Bike, Double> findAllForUserWithFilters (String search, Double minPrice, Double maxPrice, String owner, String address, Integer maxDistance, Integer minRentDays, String startDate, String endDate, String sort, Long id) throws IOException {
        var map = findBikesWithFilters(search, minPrice, maxPrice, owner, address, maxDistance, minRentDays, startDate, endDate, sort);
        map.keySet().removeIf(s-> !Objects.equals(s.getOwner().getId(), id));
        return map;
    }
}

