package midianet.journey.service;

import midianet.journey.domain.Person;
import midianet.journey.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Transactional
    public Person save(Person person){
        return repository.save(person);
    }

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
    public void delete(Long id){
        repository.delete(id);
    }

}