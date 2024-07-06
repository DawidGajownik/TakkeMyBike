package pl.coderslab.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentRepository extends JpaRepository <Rent, Long> {
    List<Rent> findByBikeId(Long bikeId);
    @Query("SELECT r FROM Rent r JOIN FETCH r.bike b JOIN FETCH b.images WHERE r.user.id = :id")
    List<Rent> findAllByUserId (@Param("id") Long id);
    @Query("SELECT r FROM Rent r JOIN FETCH r.bike b JOIN FETCH b.images WHERE b.owner.id = :id")
    List<Rent> findAllByOwnerId (@Param("id") Long id);
    Rent getRentById (Long id);
    void deleteById (Long id);
}
