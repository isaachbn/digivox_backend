package digivox.services;

import digivox.dtos.ReservationDTO;
import digivox.models.Reservation;
import digivox.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ResponseEntity<Reservation> create(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.save(reservationDTO.convertModel());
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    public ResponseEntity<?> remove(Long id) {
        return reservationRepository.findById(id)
                .map(record -> {
                    reservationRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
