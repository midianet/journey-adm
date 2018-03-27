package midianet.journey.repository;

import midianet.journey.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<Payment,Long>, JpaSpecificationExecutor {

}