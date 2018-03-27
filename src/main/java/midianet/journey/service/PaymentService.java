package midianet.journey.service;

import midianet.journey.domain.Payment;
import midianet.journey.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Transactional
    public Payment save(Payment payment){
        return repository.save(payment);
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
    public void delete(Long id){
        repository.deleteById(id);
    }

}