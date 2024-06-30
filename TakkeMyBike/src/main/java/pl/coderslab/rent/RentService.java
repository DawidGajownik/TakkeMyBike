package pl.coderslab.rent;

import org.springframework.stereotype.Service;
import pl.coderslab.user.User;
import pl.coderslab.user.UserRepository;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RentService {

    private final UserRepository userRepository;

    private final RentRepository rentRepository;

    public List<Rent> myRents (Long id) {
        return rentRepository.findAllByUserId(id);
    }
    public List<Rent> myOwns (Long id) {
        return rentRepository.findAllByOwnerId(id);
    }
    public RentService(UserRepository userRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.rentRepository = rentRepository;
    }

    public List<Rent>all () {
        return rentRepository.findAll();
    }

    public void rentBike(Rent rent) {
        Optional<User> userOptional = userRepository.findById(rent.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        rentRepository.save(rent);
    }
    public List<Rent> getRentsForBikeId (Long id) {
        return rentRepository.findByBikeId(id);
    }
    public List <LocalDate> getDatesFromRent (Rent rent) {
        List <LocalDate> dates = new ArrayList<>();
        LocalDate date = rent.getStartDate();
        while (!date.isAfter(rent.getEndDate())) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }
    public List<LocalDate> getDisabledDatesForBike(Long bikeId) {
        List <Rent> rents = getRentsForBikeId(bikeId).stream().filter(s -> s.getStatus()==2).toList();
        List<LocalDate> disabledDates = new ArrayList<>();
        for (Rent rent : rents) {
            LocalDate current = rent.getStartDate();
            while (!current.isAfter(rent.getEndDate())) {
                disabledDates.add(current);
                current = current.plusDays(1);
            }
        }
        return disabledDates;
    }
    public Rent get(Long id) {
        return rentRepository.getRentById(id);
    }
    public void update (Rent rent) {
        rentRepository.save(rent);
    }
    public void delete (Long id) {
        rentRepository.deleteById(id);
    }
}