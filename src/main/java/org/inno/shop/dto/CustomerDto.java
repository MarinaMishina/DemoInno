package org.inno.shop.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = -6581635915511739145L;

    private long id;
    private String customerStrId;
    private String name;
    private String password;
    private String phone;
    private String encryptedPassword;
    private String email;

}
