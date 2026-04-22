package com.realestate.entity;

import com.realestate.entity.enums.PropertyStatus;
import com.realestate.entity.enums.PropertyType;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType type;

    @Column(nullable = false)
    private String location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    private LocalDateTime dateListed;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore   // 🔥 prevents infinite recursion
    private User owner;

    // 🔹 DEFAULT CONSTRUCTOR
    public Property() {}

    // 🔹 PARAMETERIZED CONSTRUCTOR
    public Property(Long id, String title, String description, Double price,
                    PropertyType type, String location, String imageUrl,
                    PropertyStatus status, LocalDateTime dateListed, User owner) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.type = type;
        this.location = location;
        this.imageUrl = imageUrl;
        this.status = status;
        this.dateListed = dateListed;
        this.owner = owner;
    }

    // 🔹 AUTO SET DATE BEFORE INSERT
    @PrePersist
    public void prePersist() {
        this.dateListed = LocalDateTime.now();
        if (this.status == null) {
            this.status = PropertyStatus.PENDING;
        }
    }

    // 🔹 GETTERS & SETTERS

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

    public LocalDateTime getDateListed() { return dateListed; }
    public void setDateListed(LocalDateTime dateListed) { this.dateListed = dateListed; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}
