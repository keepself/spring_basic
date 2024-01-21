package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDTO;
import com.encore.basic.domain.MemberResponseDTO;
import com.encore.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest")
public class MemberRestController {
    @Autowired

    private final MemberService memberService;

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;

    }


    @PostMapping("/create")
    public String memberCreate(@RequestBody MemberRequestDTO memberRequestDTO) {
        memberService.save(memberRequestDTO);

        return "OK";
    }

    @GetMapping("members")
    public List<MemberResponseDTO> members() {
        return memberService.findAll();
    }


        @GetMapping("/find/{id}")
        public ResponseEntity<Map<String, Object>> memberFind(@PathVariable(value = "id")int inputid) {
        MemberResponseDTO memberResponseDTO = null;
        try{

            memberResponseDTO = memberService.memberfindById(inputid);

            return ResponseEntityController.responseMessage(memberResponseDTO,HttpStatus.OK);
        }catch(EntityNotFoundException e){
            e.printStackTrace();
            return ResponseEntityController.errorResponmessage(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public String memberDelete(@PathVariable(value = "id") int inputid) {
        memberService.delete(inputid);
        return "OK";
    }

    @PatchMapping("/update") //form 형식 Request
    public MemberResponseDTO memberUpdate(@RequestBody MemberRequestDTO memberRequestDTO) {

        memberService.memberUpdate(memberRequestDTO);
        memberService.memberfindById(memberRequestDTO.getId());
        return memberService.memberfindById(memberRequestDTO.getId());

    }

}




