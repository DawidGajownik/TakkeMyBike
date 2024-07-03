package pl.coderslab.rating;

import org.springframework.stereotype.Service;
import pl.coderslab.rent.Rent;
import pl.coderslab.user.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional

public class RatingService {
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    public void addRating (Rating rating) {
        ratingRepository.save(rating);
    }
    public List <Rating> getAllByRaterId (Long raterId){
        return ratingRepository.findAllByRaterId(raterId);
    }
    public Rating myRatingToThisRent (Long id, Rent rent) {
        return ratingRepository.findByRaterIdAndRent(id, rent);
    }
    public List <Rating> myRatings(Long id) {
        return ratingRepository.findAllByRatedId(id);
    }
}
