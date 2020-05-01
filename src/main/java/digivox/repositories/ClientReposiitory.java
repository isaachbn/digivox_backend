package digivox.repositories;

import digivox.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReposiitory extends JpaRepository<Client, Long> {
}
