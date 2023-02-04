package com.project.bookingnetic.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleType implements GrantedAuthority {


    @JsonProperty("ROLE_ADMIN")
    ROLE_ADMIN(Code.ROLE_ADMIN),
    @JsonProperty("ROLE_USER")
    ROLE_USER(Code.ROLE_USER);

    private final String authority;

    RoleType(String authority) {
        this.authority=authority;
    }
    @JsonValue
    final String authority() {
        return this.authority;
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
