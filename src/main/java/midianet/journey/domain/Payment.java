package midianet.journey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import midianet.journey.domain.converter.LocalDateConverter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	@Convert(converter = LocalDateConverter.class)
    private LocalDate dateLow;
    
    @NotNull
    @Column
    private BigDecimal amount;
    
    @NotNull
    @ManyToOne
    private Person person;
	
	public static Specification<Payment> filter(Long id, Long idPerson, LocalDate date , BigDecimal amount){
		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			Optional.ofNullable(id)      .ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get      ("id"), l)));
			Optional.ofNullable(idPerson).ifPresent(p -> predicates.add(criteriaBuilder.equal(root.<Long>get      ("person").get("id"), idPerson)));
            Optional.ofNullable(date)    .ifPresent(d -> predicates.add(criteriaBuilder.equal(root.<Date>get      ("date"),d)));
            Optional.ofNullable(amount)  .ifPresent(a -> predicates.add(criteriaBuilder.equal(root.<BigDecimal>get("amount"),a)));
			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
		};
	}
	
}