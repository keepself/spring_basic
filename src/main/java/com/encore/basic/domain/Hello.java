package com.encore.basic.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Data//getter, stter 및 toString , equals를 사전 구현
public class Hello {

    private String name;

    private String email;

    private String password;

}