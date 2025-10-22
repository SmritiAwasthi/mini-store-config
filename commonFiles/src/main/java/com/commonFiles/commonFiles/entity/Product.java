package com.commonFiles.commonFiles.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "product")
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Product implements Serializable {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    private String name;
    private String description;
    private Double price;
    @JsonProperty("stock_quantity")
    private Integer stock;
}

