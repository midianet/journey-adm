package midianet.journey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name= Datatable.SCHEMA_NAME)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = Datatable.SCHEMA_NAME,propOrder = {})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Datatable {
    public final static String SCHEMA_NAME = "datatable";
    public final static String JSON ="application/midianet.representation."+SCHEMA_NAME+"+json";
    private Long   draw;
    private Long   recordsTotal;
    private Long   recordsFiltered;
    private List   data;
    private String error;
}