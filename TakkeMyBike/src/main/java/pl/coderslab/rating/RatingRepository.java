package pl.coderslab.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.rent.Rent;

import java.util.List;

public interface RatingRepository extends JpaRepository <Rating, Long> {
    List <Rating> findAllByRatedId(Long id);
    List <Rating> findAllByRaterId(Long id);
    Rating findByRaterIdAndRent (Long id, Rent rent);
}
