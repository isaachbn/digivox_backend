package digivox.services;

import digivox.models.Rent;
import digivox.repositories.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentService {
    private final RentRepository rentRepository;

    public List<Rent> findAll() {
        return rentRepository.findAll();
    }

    public Rent save(Rent rent) {
        return rentRepository.save(rent);
    }

    public void remove(Long id) {
        rentRepository.deleteById(id);
    }

    public Optional<Rent> findById(Long id) {
        return rentRepository.findById(id);
    }
}
