package midianet.journey.resource;

import midianet.journey.domain.Datatable;
import midianet.journey.domain.Person;
import midianet.journey.domain.Photo;
import midianet.journey.repository.PersonRepository;
import midianet.journey.repository.PhotoRepository;
import midianet.journey.service.PhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/photos")
public class PhotoResource {
    private Logger log = LoggerFactory.getLogger(PhotoResource.class);

    @Autowired
    private PhotoRepository repository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PhotoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Photo> list(){
        return repository.findAll(new Sort(Sort.Direction.DESC, "date"));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Photo findById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Photo %d", id)));
    }
//
//    @PostMapping
//    @Transactional
//    @ResponseStatus(HttpStatus.CREATED)
//    public Photo create(@RequestBody Photo bedroom, HttpServletResponse response){
//        bedroom.setId(null);
//        Photo n = service.save(bedroom);
//        response.addHeader(HttpHeaders.LOCATION,String.format("/api/bedroons/%d", n.getId()));
//        return n;
//    }
//
//    @Transactional
//    @PutMapping(path = "/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Photo update(@PathVariable final Long id, @RequestBody final Photo bedroom){
//        bedroom.setId(id);
//        service.save(bedroom);
//        return bedroom;
//    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        Photo e = findById(id);
        service.delete(e.getId());
    }

    @GetMapping(path = "/paginate")
    @ResponseStatus(HttpStatus.OK)
    public Datatable paginate(@RequestParam("draw")                      Long    draw,
                              @RequestParam("start")                     Long    start,
                              @RequestParam("length")                    Integer length,
                              @RequestParam("search[value]")             String  searchValue,
                              @RequestParam("columns[0][search][value]") String  id,
                              @RequestParam("columns[1][search][value]") String  person,
                              @RequestParam("columns[2][search][value]") String  date,
                              @RequestParam("order[0][column]")          Integer order,
                              @RequestParam("order[0][dir]")             String  orderDir){
        String[] columns = new String[]{"id", "person", "date"};
        List<Map<String, Object>> data = new ArrayList<>();
        Datatable dt = new Datatable();
        Long myId = id.isEmpty() ? null : Long.parseLong(id);
        Person myPerson =  person.isEmpty() ? null : personRepository.findById(Long.parseLong(person)).orElse(new Person());
        Long myTelegram =  person.isEmpty() ? null : myPerson.getTelegram();
        LocalDate myDate = date.length() != 10 ? null : LocalDate.parse(date,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dt.setDraw(draw);
        try {
            Long qtTotal = repository.count();
            Integer page   = new Double(Math.ceil(start / length)).intValue();
            PageRequest pr = new PageRequest(page,length, new Sort(new Sort.Order(Sort.Direction.fromString(orderDir),columns[order])));
            Page<Photo> list =  !id.isEmpty() || !person.isEmpty() || !date.isEmpty() ? repository.findAll(Photo.filter(myId,myTelegram, myDate),pr) : repository.findAll(pr);
            list.forEach(p -> p.setPerson(personRepository.findByTelegram(p.getTelegram()).orElse(Person.builder().build())));
            Long qtFilter     = list.getTotalElements();
            if (qtFilter > 0) {
                list.forEach(e  -> {
                    HashMap<String  ,Object> l = new HashMap<>();
                    l.put("person"     ,e.getPerson().getName());
                    l.put("date"       ,e.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    l.put("DT_RowId"   ,"row_" + e.getId());
                    l.put("id"         ,e.getId());
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
