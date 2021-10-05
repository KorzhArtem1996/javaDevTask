package ua.korzh.testtask.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location implements Serializable {

    @Id
    @JsonProperty("place_id")
    private Long placeId;
    private Double lat;
    private Double lon;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @JsonProperty("display_name")
    private String name;
    private String type;
    @JsonProperty("class")
    private String _class;

    @Override
    public String toString() {

        return this.name;
    }
}
