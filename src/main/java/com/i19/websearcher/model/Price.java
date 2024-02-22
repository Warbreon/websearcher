package com.i19.websearcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    @JsonProperty("value")
    private String value;
    @JsonProperty("currency")
    private String currency;

}
