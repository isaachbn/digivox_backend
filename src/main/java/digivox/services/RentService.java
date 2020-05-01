package digivox.services;

import digivox.dtos.RentDTO;
import digivox.models.Item;
import digivox.models.Rent;
import digivox.repositories.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final ItemService itemService;

    @Autowired
    public RentService(RentRepository rentRepository, ItemService itemService) {
        this.rentRepository = rentRepository;
        this.itemService = itemService;
    }

    public ResponseEntity<Rent> create(RentDTO rentDTO) {
        Rent rent = rentDTO.convertModel();

        if (!itemService.rented(rent.getItem().getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return new ResponseEntity<>(rentRepository.save(rent), HttpStatus.CREATED);
    }

    public ResponseEntity<?> remove(Long id) {
        return rentRepository.findById(id)
                .map(rent -> {
                    itemService.unRented(rent.getItem().getId());
                    rentRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
