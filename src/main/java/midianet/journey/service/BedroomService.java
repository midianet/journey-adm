package midianet.journey.service;

import midianet.journey.domain.Bedroom;
import midianet.journey.domain.Person;
import midianet.journey.repository.BedroomRepository;
import midianet.journey.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BedroomService {

    @Autowired
    private BedroomRepository repository;

    @Transactional
    public Bedroom save(Bedroom bedroom){
        return repository.save(bedroom);
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
    public void delete(Long id){
        repository.delete(id);
    }

}