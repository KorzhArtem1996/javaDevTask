package ua.korzh.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    @JsonProperty("country_code")
    private String countryCode;
    private String postcode;
    private String suburb;
    private String country;
    private String state;
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Location location;
    private String footway;
    private String neighbourhood;
    @JsonProperty("city_district")
    private String cityDistrict;
    private String continent;
    @JsonProperty("house_number")
    private String houseNumber;
}

