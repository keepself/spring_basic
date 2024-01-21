package com.encore.basic.domain;

import lombok.Data;

@Data
public class MemberRequestDTO {
    private int id;
    private String name;
    private String email;
    private String password;
}
