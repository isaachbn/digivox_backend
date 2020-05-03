package digivox.services;

import digivox.models.Rent;
import digivox.repositories.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

    public List<Rent> findAllByWithdrawalPlusWeek() {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.US).dayOfWeek();
        return rentRepository.findAllByWithdrawalPlusWeek(now.with(fieldISO, 1));
    }

    public List<Rent> findRented() {
        return rentRepository.findByDeliveredIsTrue();
    }

    public Rent save(Rent rent) {
        return rentRepository.save(rent);
    }

    public Optional<Rent> findById(Long id) {
        return rentRepository.findById(id);
    }
}
