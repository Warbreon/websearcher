package com.i19.websearcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "products")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    @JsonProperty("itemId")
    private String id;

    @JsonProperty("title")
    private String name;

    @Embedded
    @JsonProperty("price")
    private Price price;

    @JsonProperty("itemHref")
    private String url;

    private String imageUrl;

    @Transient
    public Image getImage() {
        Image tempImage = new Image();
        tempImage.setImageUrl(this.imageUrl);
        return tempImage;
    }

    @JsonProperty("image")
    public void setImage(Image image) {
        this.imageUrl = (image != null) ? image.getImageUrl() : null;
    }
}
