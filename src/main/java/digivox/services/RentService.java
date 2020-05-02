package digivox.services;

import digivox.models.Rent;
import digivox.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentService {
    private final RentRepository rentRepository;

    @Autowired
    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

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
