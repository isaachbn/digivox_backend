package digivox.controllers;

import digivox.dtos.RentDTO;
import digivox.models.Rent;
import digivox.services.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"/api/rents"})
public class RentController {
    private final RentService rentService;

    @Autowired
    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public ResponseEntity<Rent> create(@RequestBody @Valid RentDTO rentDTO) {
        return rentService.create(rentDTO);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return rentService.remove(id);
    }
}
