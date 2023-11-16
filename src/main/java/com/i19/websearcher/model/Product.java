package com.i19.websearcher.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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
