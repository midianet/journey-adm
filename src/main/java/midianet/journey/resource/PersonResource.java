package midianet.journey.resource;

import midianet.journey.domain.Person;
import midianet.journey.exception.DataDependencyIntegrityException;
import midianet.journey.exception.InfraException;
import midianet.journey.exception.NotFoundException;
import midianet.journey.repository.PersonRepository;
import midianet.journey.service.PersonService;
import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
public class PersonResource {
    private Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<List<Person>> list(){
        final List<Person> list = repository.findAll(new Sort(Sort.Direction.ASC, "description"));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @GetMapping(path = "/paginate")
//    public ResponseEntity<DataTableResponse> paginate(@RequestParam("draw")                      final Long    draw,
//                                                      @RequestParam("start")                     final Long    start,
//                                                      @RequestParam("length")                    final Integer length,
//                                                      @RequestParam("search[value]")             final String  searchValue,
//                                                      @RequestParam("columns[0][search][value]") final String  id,
//                                                      @RequestParam("columns[1][search][value]") final String  name,
//                                                      @RequestParam("order[0][column]")          final Integer order,
//                                                      @RequestParam("order[0][dir]")             final String  orderDir){
//        final String[] columns          = new String[]{"id", "name"};
//        final List<Map<String, Object>> data = new ArrayList();
//        final DataTableResponse dt = new DataTableResponse();
//        final Long myId            = id.isEmpty()   ? null : Long.parseLong(id);
//        dt.setDraw(draw);
//        try {
//            final Long qtTotal = repository.count();
//            final Map<String, String> searchParams = new HashMap<>();
//            if (!searchValue.isEmpty()) {
//                searchParams.put(columns[1], searchValue);
//            }
//            final Integer page          = new Double(Math.ceil(start / length)).intValue();
//            final PageRequest pr        = new PageRequest(page,length, new Sort(new Sort.Order(Sort.Direction.fromString(orderDir),columns[order])));
//            final Page<Person> list     = !id.isEmpty() || name.isEmpty()  ? repository.findAll(Person.filter(myId,name),pr) : repository.findAll(pr);
//            final Long qtFilter         = list.getTotalElements();
//            if (qtFilter > 0) {
//                list.forEach(e  -> {
//                    final HashMap<String,Object> l = new HashMap<>();
//                    l.put("name",e.getName());
//                    l.put("DT_RowId","row_" + e.getId());
//                    l.put("id",e.getId());
//                    data.add(l);});
//            }
//            dt.setRecordsFiltered(qtFilter);
//            dt.setData(data);
//            dt.setRecordsTotal(qtTotal);
//        } catch (Exception e) {
//            log.error(e.getMessage(),e);
//            dt.setError("Datatable error "+ e.getMessage());
//        }
//        return new ResponseEntity(dt, HttpStatus.OK);
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Person> findById(@PathVariable final Long id){
        Person t;
        try {
            t = repository.findById(id);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw InfraException.raise(e);
        }
        if (t == null ) throw NotFoundException.raise("Person",id);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Person> create(@RequestBody final Person person){
        try{
            person.setId(null);
            final Person n = service.save(person);
            return new ResponseEntity<>(n,HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw InfraException.raise(e);
        }
    }

    @Transactional
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,  @RequestBody final Person person){
        person.setId(id);
        try{
            service.save(person);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw InfraException.raise(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void>delete(@PathVariable final Long id){
        try {
            final Person a = repository.findOne(id);
            if(a != null) service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(DataIntegrityViolationException e){
            log.error(e.getMessage(),e);
            throw DataDependencyIntegrityException.raise(e);
        }catch(Exception e){
            log.error(e.getMessage(),e);
            throw InfraException.raise(e);
        }
    }

}
