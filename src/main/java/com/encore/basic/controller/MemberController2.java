package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDTO;
import com.encore.basic.domain.MemberResponseDTO;
import com.encore.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member") // 공통적인 경로를 지정하는곳? 클래스차원에서 url경로지정
public class MemberController2 {
    //과제1
    //이해하고 주석달고 따라치고 테스트하기
    //함수 구조 이해하기
    //final,
    // static 공통함수를 사용하기 위해 쓰고, 객체.static함수 접근해서 사용
    @Autowired // 필드 주입방식에서 주로 @Autowired를 사용한다
    private final MemberService memberService;

    // MemberSerVice 의 붕어빵 memberService 생성
    // (생성한 이유는?) 아래코드의 의존성을 주입하기위한 생성?
    @Autowired
    public MemberController2(MemberService memberService) {
        this.memberService = memberService;
        //생성자를 통한 의존성주입 코드
    }

    //url경로지정 : localhost:8080/member/create-secreen2
    //@ResponseBody 없고
    @GetMapping("/create-screen2")
    public String SignUpList() {

        return "member/member-create";
        //@ResponseBody 없고 String타입을 갖고 있기 때문에 위의 경로html파일 반환

    }

    @PostMapping("/create") //form형식 회원가입 요청
//    포스트매핑은 프로트와의 경로약속? 이라하며 CRUD에서 C와 insert, 사용된되곤한다?
    //@RequestBody가 있어야 하는데 form 형식으로 받아올 때는 안 붙여도 된다.
    //MemberRequestDTO는 왜 쓸까? member entity를 바로 사용하면 DB와 연결되어 목적적합하지 않다.
    //MemberRequestDTO의 목적은 사용자의 입력값을 받아와서 service에게 전달하는 것이다
    public String Signup(MemberRequestDTO memberRequestDTO) {
//        회원가입입에 기입한 data형식을 DTO를통해 받아와서 객체형식으로 만든다.
        //         멤버서비스안에 세이브 함수에 DTO를 파라미터로 넘겨줘서 호출한다. 반환값이 VOID기때문에
//        반환값을 담지 않는다.
        memberService.save(memberRequestDTO);

        return "redirect:/member/members";

    }

    @GetMapping("members")
    public String members(Model model) {
//  Model은 Spring에서 제공하는 데이터를 뷰에 전달하기 위한 컨테이너고. 이를 통해 데이터를 모델에 추가하고, 뷰에 전달.
        model.addAttribute("memberList", memberService.findAll());
//  모델에 "memberList"라는 속성 이름으로 회원 목록을 추가함
        return "memeber/member-list";
    }

    @GetMapping("/")
    public String home() {
        return "member/header";
//    홈화면

    }

    @GetMapping("/find")
    public String MemberFind(@RequestParam(value = "id") int inputid, Model model) {
// localhost:8080/member/find?id=xxxx
        MemberResponseDTO memberResponseDTO = memberService.memberfindById(inputid);
        model.addAttribute("memeber", memberResponseDTO);
        return "member/member-detalil";

    }

    @GetMapping("delete")
    public String deleteMember(@RequestParam(value = "id") int inputid) {
        memberService.delete(inputid);
        return "redirect:/member/members";
    }

}
