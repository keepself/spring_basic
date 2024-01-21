package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("response/entity")
public class ResponseEntityController {

    //    @ResponseStatus 어노테이션방식
    @GetMapping("status")
    @ResponseStatus(HttpStatus.CREATED)
    public String reponseStatus() {
        return "OK";
    }

    @GetMapping("status2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member reponseStatus2() {

        Member member = new Member("llee", "llee@nave.rc", "1234");

        return member; //
    }

    @GetMapping("custom")
    public ResponseEntity<Member> custom1() {

        Member member = new Member("llee", "llee@nave.rc", "1234");

        return new ResponseEntity<>(member, HttpStatus.CREATED); //
    }

    //   RespoonseEntity<String>일경우 text/html 설정
    @GetMapping("custom2")
    public ResponseEntity<String> custom2() {
        String html = "<h1>없는 ID입니다.</h1>";
        return new ResponseEntity<>(html, HttpStatus.NOT_FOUND); //
    }

    //    map형태의 메시지 커스텀

    public static ResponseEntity<Map<String, Object>> errorResponmessage(HttpStatus status, String error) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("error message", error);
        return new ResponseEntity<>(body, status);
    }
//    status 201 message : 객체, map_custom2


    public static ResponseEntity<Map<String, Object>> responseMessage(Object object, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("status message", object);
        body.put("error message", object);

        return new ResponseEntity<>(body, status);

    }

    //    메서드 체이닝 : ResponseEntity의 클래스메서드 사용
    @GetMapping("chaing1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("llee", "llee@nave.rc", "1234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("chaing2")
    public ResponseEntity<String> chaining2() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("chaing3")
    public ResponseEntity<Member> chaining3() {
        Member member = new Member("llee", "llee@nave.rc", "1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }
}