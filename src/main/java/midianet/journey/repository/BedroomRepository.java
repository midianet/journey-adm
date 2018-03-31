package midianet.journey.repository;

import midianet.journey.domain.Bedroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BedroomRepository extends JpaRepository<Bedroom,Long>, JpaSpecificationExecutor {
	Optional<Bedroom> findById(Long id);
}