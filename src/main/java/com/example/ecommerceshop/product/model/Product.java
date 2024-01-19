package com.example.ecommerceshop.product.model;

import com.example.ecommerceshop.cart.model.Cart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String productName;

    private String productDecription;
    @NotBlank
    private double productPrice;

    private Integer stockQuantity;

    public Product() {
    }

    public Product(String productName, String productDecription, double productPrice, Integer stockQuantity) {
        this.productName = productName;
        this.productDecription = productDecription;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
    }

    public Product(Integer id, String productName, String productDecription, double productPrice, Integer stockQuantity) {
        this.id = id;
        this.productName = productName;
        this.productDecription = productDecription;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDecription() {
        return productDecription;
    }

    public void setProductDecription(String productDecription) {
        this.productDecription = productDecription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
