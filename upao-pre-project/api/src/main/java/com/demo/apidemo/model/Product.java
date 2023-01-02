package com.demo.apidemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", length = 60, nullable = false)
    private String name;
    @Column(name="price",  nullable = false)
    private int price;
    @Column(name="account",  nullable = false)
    private int account;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column( name ="picture")
    private byte[] picture;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "categories_id", nullable = false)
    @JsonIgnoreProperties( {"hibernateLazyInitializer", "handler"})
    private Category category;
}
