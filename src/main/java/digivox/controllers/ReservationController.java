package digivox.controllers;

import digivox.dtos.ReservationDTO;
import digivox.models.Client;
import digivox.models.Reservation;
import digivox.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/reservations"})
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List findAll() {
        return reservationService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Reservation> reservationOptional = reservationService.findById(id);

        return reservationOptional
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation reservationModel = reservationService.save(reservationDTO.convertModel());
        return new ResponseEntity<>(reservationModel, HttpStatus.CREATED);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Reservation> reservationModel =  reservationService.findById(id);

        return reservationModel
                .map(reservation -> {
                    reservationService.remove(reservation);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
