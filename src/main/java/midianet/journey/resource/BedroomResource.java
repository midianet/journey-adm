package midianet.journey.resource;

import midianet.journey.domain.Bedroom;
import midianet.journey.domain.Datatable;
import midianet.journey.repository.BedroomRepository;
import midianet.journey.service.BedroomService;
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
@RequestMapping("/api/bedroons")
public class BedroomResource {
    private Logger log = LoggerFactory.getLogger(BedroomResource.class);

    @Autowired
    private BedroomRepository repository;

    @Autowired
    private BedroomService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Bedroom> list(){
        return repository.findAll(new Sort(Sort.Direction.ASC, "description"));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bedroom findById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Bedroom %d", id)));
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public Bedroom create(@RequestBody Bedroom bedroom, HttpServletResponse response){
        bedroom.setId(null);
        Bedroom n = service.save(bedroom);
        response.addHeader(HttpHeaders.LOCATION,String.format("/api/bedroons/%d", n.getId()));
        return n;
    }

    @Transactional
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Bedroom update(@PathVariable final Long id, @RequestBody final Bedroom bedroom){
        bedroom.setId(id);
        service.save(bedroom);
        return bedroom;
    }

    @Transactional
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id){
        Bedroom e = findById(id);
        service.delete(e.getId());
    }

    @GetMapping(path = "/paginate")
    @ResponseStatus(HttpStatus.OK)
    public Datatable paginate(@RequestParam("draw")                      Long    draw,
                              @RequestParam("start")                     Long    start,
                              @RequestParam("length")                    Integer length,
                              @RequestParam("search[value]")             String  searchValue,
                              @RequestParam("columns[0][search][value]") String  id,
                              @RequestParam("columns[1][search][value]") String  description,
                              @RequestParam("columns[2][search][value]") String  type,
                              @RequestParam("columns[3][search][value]") String  gender,
                              @RequestParam("order[0][column]")          Integer order,
                              @RequestParam("order[0][dir]")             String  orderDir){
        String[] columns = new String[]{"id", "description", "type", "gender"};
        List<Map<String, Object>> data = new ArrayList<>();
        Datatable dt = new Datatable();
        Long myId = id.isEmpty() ? null : Long.parseLong(id);
        String myGender =  gender.isEmpty() ? null : gender;
        String myType   =  type.isEmpty()   ? null : type;
        dt.setDraw(draw);
        try {
            Long qtTotal = repository.count();
            Integer page      = new Double(Math.ceil(start / length)).intValue();
            PageRequest pr    = PageRequest.of(page,length,Sort.by(Sort.Direction.fromString(orderDir),columns[order]));
            Page<Bedroom> list =  !id.isEmpty() || !description.isEmpty() || !type.isEmpty() || !gender.isEmpty() ? repository.findAll(Bedroom.filter(myId,description, myType, myGender),pr) : repository.findAll(pr);
            Long qtFilter     = list.getTotalElements();
            if (qtFilter > 0) {
                list.forEach(e  -> {
                    HashMap<String  ,Object> l = new HashMap<>();
                    l.put("description",e.getDescription());
                    l.put("type"       ,e.getType().getDescription());
                    l.put("gender"     ,e.getGender().getDescription());
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
