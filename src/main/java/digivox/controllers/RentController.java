package digivox.controllers;

import digivox.dtos.RentDTO;
import digivox.models.Rent;
import digivox.services.ItemService;
import digivox.services.RentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/rents"})
@AllArgsConstructor
public class RentController {
    private final RentService rentService;
    private final ItemService itemService;

    @GetMapping
    public List findAll(
            @RequestParam(name = "delivered", defaultValue = "false", required = false) Boolean delivered,
            @RequestParam(name = "rented", defaultValue = "false", required = false) Boolean rented
    ) {
        if (rented) {
            return rentService.findAllByWithdrawalPlusWeek();
        }

        if (delivered) {
            return rentService.findRented();
        }

        return rentService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Rent> rentOptional = rentService.findById(id);

        return rentOptional
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

    @PatchMapping(path = "/{id}/deliver")
    public ResponseEntity<Rent> deliver(@PathVariable long id) {
        Optional<Rent> rentOptional = rentService.findById(id);
        return rentOptional
                .map(rent -> {
                    itemService.unRented(rent.getItem().getId());
                    rent.setDelivered(true);
                    rentService.save(rent);
                    return ResponseEntity.ok().body(rent);
                }).orElse(ResponseEntity.notFound().build());

    }
}
