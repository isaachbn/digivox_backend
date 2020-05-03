package digivox.repositories;

import digivox.models.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query("select r from rents r where r.withdrawal >= :datePlusWeek and r.delivered=false")
    List<Rent> findAllByWithdrawalPlusWeek(@Param("datePlusWeek") LocalDate datePlusWeek);

    List<Rent> findByDeliveredIsTrue();
}
