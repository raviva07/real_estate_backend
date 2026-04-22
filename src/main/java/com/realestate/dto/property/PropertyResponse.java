package com.realestate.dto.property;

import com.realestate.entity.enums.PropertyStatus;
import com.realestate.entity.enums.PropertyType;

public class PropertyResponse {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private PropertyType type;
    private String location;
    private String imageUrl;
    private PropertyStatus status;
    private String ownerName;

    public PropertyResponse() {}

    public PropertyResponse(Long id, String title, String description,
                            Double price, PropertyType type,
                            String location, String imageUrl,
                            PropertyStatus status, String ownerName) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.imageUrl = imageUrl;
        this.status = status;
        this.ownerName = ownerName;
    }

    // GETTERS & SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public PropertyType getType() { return type; }
    public void setType(PropertyType type) { this.type = type; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public PropertyStatus getStatus() { return status; }
    public void setStatus(PropertyStatus status) { this.status = status; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
}
