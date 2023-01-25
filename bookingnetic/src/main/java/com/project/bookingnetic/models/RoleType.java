package com.project.bookingnetic.models;


import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.security.core.GrantedAuthority;


public enum RoleType implements GrantedAuthority {

    /*
    ADMIN(Code.ADMIN),
    USER(Code.USER);*/

    ROLE_ADMIN(Code.ROLE_ADMIN),
    ROLE_USER(Code.ROLE_USER);

    private final String authority;

    RoleType(String authority) {
        this.authority=authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
    public class Code {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
    }

}
