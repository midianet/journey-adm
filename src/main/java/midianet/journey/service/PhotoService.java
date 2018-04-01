package midianet.journey.service;

import midianet.journey.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository repository;

    @Transactional(rollbackOn = {DataIntegrityViolationException.class, Exception.class})
    public void delete(Long id){
        repository.delete(id);
    }

}