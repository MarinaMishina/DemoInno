package org.inno.shop.ui.request;

import lombok.Data;

@Data
public class CustomerRequestModel {

    private String name;
    private String password;
    private String phone;
    private String email;

}
