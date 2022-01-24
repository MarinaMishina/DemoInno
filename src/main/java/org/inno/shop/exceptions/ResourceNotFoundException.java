package org.inno.shop.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1180032147308274860L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
