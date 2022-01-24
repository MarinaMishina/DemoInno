package org.inno.shop.entity;


import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1080032147308274960L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String customerStrId;

    @Column (nullable = false, length = 50)
    private String name;

    @Column (nullable = false, length = 50)
    private String password;

    @Column (nullable = false, length = 15)
    private String phone;

    @Column (nullable = false, length = 255)
    private String encryptedpassword;

    @Column (nullable = false, length = 120)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Orders> ordersList;
}
