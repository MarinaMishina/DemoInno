package org.inno.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity(name = "products")
public class Products implements Serializable{

    private static final long serialVersionUID = 1090032147308274960L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String sku;

    @Column (nullable = false, length = 255)
    private String name;

    @Column (nullable = false)
    private double price;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Orders> orders = new HashSet<>();

}
