package midianet.journey.repository;

import midianet.journey.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long>, JpaSpecificationExecutor {
	Optional<Person> findById(Long id);
}