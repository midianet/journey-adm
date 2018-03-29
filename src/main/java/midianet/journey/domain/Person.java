package midianet.journey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @Column(nullable = false)
    private Long telegram;

    @NotNull
    @NotEmpty
    @Column(nullable = false, length = 80)
    private String name;

    @NotNull
    @NotEmpty
    @Column(nullable = false, length = 40)
    private String nickname;

    @Column(length = 20)
    private String phone;

    @Column(length = 11)
    private String cpf;

    @Column(length = 10)
    private String rg;

    @Column(name = "rg_exped", length = 15)
    private String rgexped;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate register;

    private Integer assent;

    @Past
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthday;

    @NotNull
    @Convert(converter = SexConverter.class)
    @Column(nullable = false,length = 1)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Sex sex;

    @NotNull
    @Column(length = 1,nullable = false)
    @Convert(converter = ContractConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Contract agreed;

    @NotNull
    @Column(length = 1,nullable = false)
    @Convert(converter = StateConverter.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private State state;
    
    @ManyToOne
    private Bedroom bedroom;
    
    @Getter
    @AllArgsConstructor
    public enum Sex {
        MALE  ("M","Masculino"),
        FEMALE("F","Feminino");

        @JsonValue
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

        @JsonValue
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
        BLANK  ("N" ,"Não Assinado"),
        AGREE  ("A","Aceito"),
        DISAGRE("D","Não Aceito");

        @JsonValue
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
	
	public static Specification<Person> filter(final Long id, final String name, final String sex, final Integer state) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			final List<Predicate> predicates = new ArrayList<>();
			Optional.ofNullable(id)   .ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get("id"), l)));
			Optional.ofNullable(name) .ifPresent(n -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%")));
            Optional.ofNullable(sex)  .ifPresent(s -> predicates.add(criteriaBuilder.equal(root.<Person.Sex>get("sex"), Person.Sex.toEnum(s))));
            Optional.ofNullable(state).ifPresent(s -> predicates.add(criteriaBuilder.equal(root.<Person.State>get("state"),Person.State.toEnum(s))));
			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
		};
	}
	
}