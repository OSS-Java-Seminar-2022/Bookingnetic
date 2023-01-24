package com.project.bookingnetic.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.EnumUtils;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleType implements GrantedAuthority {
    @JsonProperty("ROLE_ADMIN")
    ADMIN(Code.ADMIN),
    @JsonProperty("ROLE_USER")
    USER(Code.USER);

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
       // @JsonProperty("ROLE_ADMIN")
        public static final String ADMIN = "ROLE_ADMIN";
        //@JsonProperty("ROLE_USER")
        public static final String USER = "ROLE_USER";
    }

//    @JsonCreator
//    static RoleType findValue(@JsonProperty("ROLE_ADMIN") String admin, @JsonProperty("ROLE_USER") String user) {
//        return Arrays.stream(RoleType.values()).filter(pt -> pt.ADMIN.equals(admin) && pt.USER.equals(user)).findFirst().get();
//    }

}
