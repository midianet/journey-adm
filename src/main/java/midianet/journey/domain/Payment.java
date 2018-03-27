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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
    
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateLow;
    
    @NotNull
    @Column(length = 3,precision = 2)
    private BigDecimal amount;
    
    @NotNull
    @ManyToOne
    private Person person;
	
//	public static Specification<Payment> filter(final Long id, final String name) {
//		return (root, criteriaQuery, criteriaBuilder) -> {
//			final List<Predicate> predicates = new ArrayList<>();
//			Optional.ofNullable(id).ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get("id"), l)));
//			Optional.ofNullable(name).ifPresent(s -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + s.toLowerCase() + "%")));
//			return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
//		};
//	}
	
}