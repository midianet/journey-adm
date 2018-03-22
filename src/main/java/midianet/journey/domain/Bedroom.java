package midianet.journey.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
//@Entity
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
    
    private List<Person> occupants;
    
    @Enumerated
    private Type         type;
    
    @Enumerated
    private Gender       gender;
    
    @Getter
    @AllArgsConstructor
    public enum Type{
        DOUBLE    (2,"Quarto Duplo"),
        TRIPLE    (3,"Quarto Triplo"),
        QUADRUPLE (4,"Quarto Quádruplo");
        private Integer value;
        private String  description;
        
        public static Bedroom.Type toEnum(Integer value){
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
        ROOM_FEMALE (0,"Feminino"),
        ROOM_MALE   (1,"Masculino"),
        ROOM_COUPLE (2,"Casal");
        private Integer value;
        private String  description;
        
        public static Bedroom.Gender toEnum(Integer value){
            for (Bedroom.Gender e : Bedroom.Gender.values()) {
                if(e.value.equals(value)){
                    return e;
                }
            }
            return null;
        }
        
    }
    
}