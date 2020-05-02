package digivox.controllers;

import digivox.dtos.RentDTO;
import digivox.models.Rent;
import digivox.models.Reservation;
import digivox.services.ItemService;
import digivox.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/rents"})
public class RentController {
    private final RentService rentService;
    private final ItemService itemService;

    @Autowired
    public RentController(RentService rentService, ItemService itemService) {
        this.rentService = rentService;
        this.itemService = itemService;
    }

    @GetMapping
    public List findAll() {
        return rentService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Rent> rebOptional = rentService.findById(id);

        return rebOptional
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rent> create(@RequestBody @Valid RentDTO rentDTO) {
        if (!itemService.rented(rentDTO.getItem())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Rent rentModel = rentService.save(rentDTO.convertModel());

        return new ResponseEntity(rentModel, HttpStatus.CREATED);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Rent> rentModel = rentService.findById(id);
        return rentModel
                .map(rent -> {
                    itemService.unRented(rent.getItem().getId());
                    rentService.remove(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
