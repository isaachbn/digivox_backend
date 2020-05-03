package digivox.services;

import digivox.models.Rent;
import digivox.repositories.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentService {
    private final RentRepository rentRepository;

    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    public List<Rent> findAllRentedWeekly() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.US).dayOfWeek();
        return rentRepository.findAllRentedOrDeliveredWeekly(now.with(fieldISO, 1), false);
    }

    public List<Rent> findAllDeliveredWeekly() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.US).dayOfWeek();
        return rentRepository.findAllRentedOrDeliveredWeekly(now.with(fieldISO, 1), true);
    }

    public Rent save(Rent rent) {
        return rentRepository.save(rent);
    }

    public Optional<Rent> findById(Long id) {
        return rentRepository.findById(id);
    }
}
