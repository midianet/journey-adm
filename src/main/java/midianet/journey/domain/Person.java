package midianet.journey.domain;

import lombok.*;
import midianet.journey.domain.converter.ContractConverter;
import midianet.journey.domain.converter.SexConverter;
import midianet.journey.domain.converter.StateConverter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NotEmpty
    @Column(nullable = false)
    private Long telegram;
    
    private String name;
    private String nickname;
    private String phone;
    private String cpf;
    private String rg;
    private LocalDate register;
    private Integer assent;
    
    @Column(name = "rg_exped")
    private String rgexped;
    
    @Past
    private LocalDate birthday;
    
    @Convert(converter = SexConverter.class)
    private Sex sex;

    @Convert(converter = ContractConverter.class)
    private Contract agreed;

    @Convert(converter = StateConverter.class)
    private State state;
    
    //private Bedroom   bedroom;
    
    @Getter
    @AllArgsConstructor
    public enum Sex {
        MALE  ("M","Masculino"),
        FEMALE("F","Feminino");
        private String value;
        private String description;
        
        public static Sex toEnum(String value){
            for (Sex e : Sex.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
    
    @Getter
    @AllArgsConstructor
    public enum State{
        WAITING   (0,"Candidato"),
        SELECTED  (1,"Selecionado"),
        ASSOCIATE (2,"Associado"),
        REGISTERED(3,"Registrado"),
        CONFIRMED (4,"Confirmado");
        private Integer value;
        private String  description;
        
        public static State toEnum(Integer value) {
            for (State e : State.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
    
    @Getter
    @AllArgsConstructor
    public enum Contract{
        BLANK  ("" ,"Não Assinado"),
        AGREE  ("A","Aceito"),
        DISAGRE("D","Não Aceito");
        private String value;
        private String description;
        
        public static Contract toEnum(String value) {
            for (Contract e : Contract.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
	
	public static Specification<Person> filter(final Long id, final String name) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();
			Optional.ofNullable(id).ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get("id"), l)));
			Optional.ofNullable(name).ifPresent(s -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + s.toLowerCase() + "%")));
			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
		};
	}
	
}