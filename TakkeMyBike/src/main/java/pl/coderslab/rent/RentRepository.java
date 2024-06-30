package pl.coderslab.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.user.User;

import java.util.List;

public interface RentRepository extends JpaRepository <Rent, Long> {
    List<Rent> findByBikeId(Long bikeId);
    List<Rent> findAllByUserId (Long userId);
    List<Rent> findAllByOwnerId (Long id);
    Rent getRentById (Long id);
    void deleteById (Long id);
}
