package com.project.bookingnetic.models;


import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.security.core.GrantedAuthority;


public enum RoleType implements GrantedAuthority {
    ADMIN(Code.ADMIN),
    USER(Code.USER);

    private final String authority;

    RoleType(String authority) {
        this.authority=authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
    public class Code {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }

}
