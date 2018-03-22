package midianet.journey.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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
    
    private String    name;
    private String    nickname;
    private String    phone;
    private String    birthday;
    private String    cpf;
    private String    rg;
    private String    rgexped;
    private LocalDate register;
    private Integer   assent;
    
    @Enumerated
    @Convert(converter = SexConverter.class)
    private Sex       sex;

    @Enumerated
    @Convert(converter = ContractConverter.class)
    private Contract  agreed;
    
    @Enumerated
    @Convert(converter = StateConverter.class)
    private State     state;

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
        BLANK("","Não Assinado"),
        AGREE("A","Aceito"),
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
    
    
}
