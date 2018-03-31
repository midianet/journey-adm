package midianet.journey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import midianet.journey.domain.converter.GenderConverter;
import midianet.journey.domain.converter.TypeConverter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bedroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(nullable = false,length = 30)
    private String       description;
    
    @Transient
    private List<Person> occupants;
    
    @NotNull
    @Column(length = 1,nullable = false)
    @Convert(converter = TypeConverter.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Type         type;
    
    @NotNull
    @Column(length = 1,nullable = false)
    @Convert(converter = GenderConverter.class)
    //@JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender       gender;
    
    @Getter
    @AllArgsConstructor
    public enum Type{
        DOUBLE    ("D","Quarto Duplo"),
        TRIPLE    ("T","Quarto Triplo"),
        QUADRUPLE ("Q","Quarto Quádruplo");
        
        private String value;
        private String  description;
    
        @JsonValue
        public String getValue(){
            return value;
        }
        
        public static Bedroom.Type toEnum(String value){
            for (Bedroom.Type e : Bedroom.Type.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
    
    @Getter
    @AllArgsConstructor
    public enum Gender{
        ROOM_FEMALE ("F","Feminino"),
        ROOM_MALE   ("M","Masculino"),
        ROOM_COUPLE ("U","Misto");
    
    
        private String value;
        private String  description;
    
        @JsonValue
        public String getValue(){
            return value;
        }
        
        public static Bedroom.Gender toEnum(String value){
            for (Bedroom.Gender e : Bedroom.Gender.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
    
    public static Specification<Bedroom> filter( Long id, String description, String type, String gender) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(id)         .ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get("id"), l)));
            Optional.ofNullable(description).ifPresent(n -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + n.toLowerCase() + "%")));
            Optional.ofNullable(type)       .ifPresent(s -> predicates.add(criteriaBuilder.equal(root.<Person.Sex>get("type")    ,Bedroom.Type  .toEnum(s))));
            Optional.ofNullable(gender)     .ifPresent(s -> predicates.add(criteriaBuilder.equal(root.<Person.State>get("gender"),Bedroom.Gender.toEnum(s))));
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
    
    
}