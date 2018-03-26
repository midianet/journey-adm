package midianet.journey.resource;

import midianet.journey.domain.Datatable;
import midianet.journey.domain.Person;
import midianet.journey.repository.PersonRepository;
import midianet.journey.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/persons")
public class PersonResource {
    private Logger log = LoggerFactory.getLogger(PersonResource.class);

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> list(){
        return repository.findAll(new Sort(Sort.Direction.ASC, "description"));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person findById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Pessoa %d", id)));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person, HttpServletResponse response){
        person.setId(null);
        Person n = service.save(person);
        response.addHeader(HttpHeaders.LOCATION,String.format("/api/persons/%d", n.getId()));
        return n;
    }

    @Transactional
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person update(@PathVariable final Long id, @RequestBody final Person person){
        person.setId(id);
        service.save(person);
        return person;
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        Person e = findById(id);
        service.delete(e.getId());
    }

    @GetMapping(path = "/paginate")
    @ResponseStatus(HttpStatus.OK)
    public Datatable paginate(@RequestParam("draw")                      Long    draw,
                              @RequestParam("start")                     Long    start,
                              @RequestParam("length")                    Integer length,
                              @RequestParam("search[value]")             String  searchValue,
                              @RequestParam("columns[0][search][value]") String  id,
                              @RequestParam("columns[1][search][value]") String  name,
                              @RequestParam("columns[2][search][value]") String  sex,
                              @RequestParam("columns[3][search][value]") String  state,
                              @RequestParam("order[0][column]")          Integer order,
                              @RequestParam("order[0][dir]")             String  orderDir){
        String[] columns = new String[]{"id", "name", "sex", "state"};
        List<Map<String, Object>> data = new ArrayList<>();
        Datatable dt = new Datatable();
        Long myId = id.isEmpty() ? null : Long.parseLong(id);
        dt.setDraw(draw);
        try {
            Long qtTotal = repository.count();
            Integer page      = new Double(Math.ceil(start / length)).intValue();
            PageRequest pr    = PageRequest.of(page,length,Sort.by(Sort.Direction.fromString(orderDir),columns[order]));
            Page<Person> list = !id.isEmpty() || !name.isEmpty() ? repository.findAll(Person.filter(myId,name),pr) : repository.findAll(pr);
            Long qtFilter     = list.getTotalElements();
            if (qtFilter > 0) {
                list.forEach(e  -> {
                    HashMap<String,Object> l = new HashMap<>();
                    l.put("state",e.getState().getDescription());
                    l.put("sex"  ,e.getSex().getDescription());
                    l.put("name" ,e.getName());
                    l.put("DT_RowId","row_" + e.getId());
                    l.put("id"   ,e.getId());
                    data.add(l);});
            }
            dt.setRecordsFiltered(qtFilter);
            dt.setData(data);
            dt.setRecordsTotal(qtTotal);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            dt.setError("Datatable error "+ e.getMessage());
        }
        return dt;
    }

}
