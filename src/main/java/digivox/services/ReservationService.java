package digivox.services;

import digivox.models.Reservation;
import digivox.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void remove(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }
}
