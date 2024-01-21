package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDTO;
import com.encore.basic.domain.MemberResponseDTO;
import com.encore.basic.repository.MemberRepository;
import com.encore.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

//service어노테이션을 통해 싱글톤 컴포넌트로 생성-> 스프링 빈으로 등록
//스픠링 빈이란 스프링이 생성하고 관리하는 객체를 의미하ㄴ
//제어의 역전(Inbersion of Control -? IOC컨테이너가 스프링빈을 관리(빈을 생성 의존성 주입)
@Controller
@RequestMapping("/member1")
public class MemberController {
    @Autowired  //의존성 주입(DI) 방법1 => "필드주입방식"
//    private MemberService memberService;
//@Service
//    의존성 주입방법2 => "생성자주입방식" ,가장 많이 사용하는 방법
//    장점 : final을 통해 상수로 사용가능, 다형성 구현 가능 , 순환참조방지
//    생성자가 1개밖에 없을때에는 autowired 생략가능
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;

    }
//    의존성주입방법3 @RequiredArgsConstructor를 이용한 방식
    //@RequiredArgsConstructor :@NonNull어노테이션이 붙어있는
    // 필드 또는 final 초기화되지 않은 final 대상으로 생성자 생성.

//    MemberController() {
//        memberService = new MemberService();
//
//    }



    @GetMapping("/create-screen")
    public String listSignUp() {

        return "member/member-create";

    }

    @PostMapping("/create") //form 형식 회원가입요청을(Request)받는다. 회원가입하기위한것/
    public String Signup(MemberRequestDTO memberRequestDTO) {
//        트렌젝션 및 예외처리테스트
//        try {
//            memberService.save(memberRequestDTO);
////        url 리다이렉트
//            return "redirect:/member/members";
//
//        } catch (IllegalArgumentException e) {
//            return "404-error-page";
//        }

        memberService.save(memberRequestDTO);
//        url 리다이렉트
        return "redirect:/member/members";
    }

    @GetMapping("members")
    public String members(Model model) {
        model.addAttribute("memberList", memberService.findAll());
        return "member/member-list";
    }

    @GetMapping("/")
    public String home() {
        return "member/header";
    }

    @GetMapping("/find")
    public String memberFind(@RequestParam(value = "id") int inputid, Model model) {

        MemberResponseDTO memberResponseDTO = memberService.memberfindById(inputid);
        model.addAttribute("member", memberResponseDTO);
        return "member/member-detail";

    }

    @GetMapping("delete")
    public String deleteMember(@RequestParam(value = "id") int inputid) {
        memberService.delete(inputid);
        return "redirect:/member/members";
    }

    @PostMapping("/update") //form 형식 Request
    public String memberUpdate(MemberRequestDTO memberRequestDTO) {

        memberService.memberUpdate(memberRequestDTO);
        return "redirect:/member/find?id=" + memberRequestDTO.getId();
    }


    }






