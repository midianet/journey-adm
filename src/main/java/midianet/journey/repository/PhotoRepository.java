package midianet.journey.repository;

import midianet.journey.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo,Long>, JpaSpecificationExecutor {
	Optional<Photo> findById(Long id);
}