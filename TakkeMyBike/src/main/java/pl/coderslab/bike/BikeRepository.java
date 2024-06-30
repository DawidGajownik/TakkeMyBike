package pl.coderslab.bike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.user.User;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository <Bike, Long> {
    List<Bike> findAllByOwner (User user);
}
