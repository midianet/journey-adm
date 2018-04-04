package midianet.journey.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import groovy.lang.Lazy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import midianet.journey.domain.converter.LocalDateConverter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private Long telegram;
    
    @NotNull
    @Column(nullable = false)
    private byte[] photo;
    
    @NotNull
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;
    
    @Transient
    private Person person;
    
    public static Specification<Photo> filter(Long id, Long telegram, LocalDate date) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(id)       .ifPresent(l -> predicates.add(criteriaBuilder.equal(root.<Long>get("id"), l)));
            Optional.ofNullable(telegram) .ifPresent(n -> predicates.add(criteriaBuilder.equal(root.<Long>get("telegram"), telegram)));
            Optional.ofNullable(date)     .ifPresent(s -> predicates.add(criteriaBuilder.equal(root.<LocalDate>get("date"), date)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

}