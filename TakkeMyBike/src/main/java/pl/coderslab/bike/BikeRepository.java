package pl.coderslab.bike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.user.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository <Bike, Long> {
    List<Bike> findAllByOwner (User user);

    @Query("""
            select distinct b from Bike b join b.rents r where
            (:search is null or (b.title like %:search% or b.description like %:search%)) and
            (:maxPrice is null or b.pricePerDay <= :maxPrice) and
            (:minPrice is null or b.pricePerDay >= :minPrice) and
            (:owner is null or
            b.owner.email like %:owner% or
            b.owner.firstName like %:owner% or
            b.owner.lastName like %:owner% or
            b.owner.login like %:owner% or
            b.owner.telephoneNumber like %:owner%) and
            (:minRentDays is null or b.minRentDays <= :minRentDays) and
            ((:startDate is null or :endDate is null) or
            NOT EXISTS (SELECT 1 FROM Rent r2 WHERE r2.bike = b AND r2.status = 2
            AND ((:startDate >= r2.startDate AND :startDate <= r2.endDate)
            or (:endDate >= r2.startDate AND :endDate <= r2.endDate)
            or (:startDate <= r2.startDate and :endDate >= r2.endDate))))
            
            """)

    List<Bike> findAllWithFilters(@Param("search") String search,
                                  @Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice,
                                  @Param("owner") String owner,
                                  @Param("minRentDays") Integer minRentDays,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

//    ;
//                                  @Param("address") String address,
//                                  @Param("maxDistance") Integer maxDistance,

}
