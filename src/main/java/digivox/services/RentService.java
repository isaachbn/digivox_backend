package digivox.services;

import digivox.models.Rent;
import digivox.repositories.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentService {
    private final RentRepository rentRepository;

    public List<Rent> findAllByWithdrawalPlusWeek() {
        LocalDate localDateNow = LocalDate.now();
        LocalDate localDateWeek = LocalDate.now().plusDays(7);
        return rentRepository.findAllByWithdrawalPlusWeek(localDateNow, localDateWeek);
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
