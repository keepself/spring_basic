package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDTO;
import com.encore.basic.domain.MemberResponseDTO;
import com.encore.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
//service어노테이션을 통해 싱글톤 컴포넌트로 생성-> 스프링 빈으로 등록
//스픠링 빈이란 스프링이 생성하고 관리하는 객체를 의미하ㄴ
//제어의 역전(Inbersion of Control -? IOC컨테이너가 스프링빈을 관리(빈을 생성 의존성 주입)
public class MemberService {

    //우리는 인터페이스 타입 MemberRepository를 .memberRepository변수로 선언해서사용하기로했다.
    private MemberRepository memberRepository;

    //memberRepository를 memoryMemberRepository로 초기화 해줄건데 이게 가능한이유는 인터페이스 MemberRepository를 MemoryMemberRepository가
//    구현했기 때문이다.
    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.memberRepository = springDataJpaMemberRepository;
    }


    public List<MemberResponseDTO> findAll() { //전체회원 목록 반환
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDTO> memberResponseDTOS = new ArrayList<>();
        for (Member member : members) {
            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
            memberResponseDTO.setId(member.getId());
            memberResponseDTO.setName(member.getName());
            memberResponseDTO.setEmail(member.getEmail());
            memberResponseDTOS.add(memberResponseDTO);
        }
        return memberResponseDTOS;
    }

    //Transactional 어노테이션 클래스 단위로 붙이면 모든 메서드에 가각Transaction적용
    //Transctional을 적용하면 한 메서드 단위로 트랜젝션 지정
    @Transactional
    public void save(MemberRequestDTO memberRequestDTO) {       //회원가입


//        try { Member member = new Member (memberRequestDTO.getName(), memberRequestDTO.getEmail(), memberRequestDTO.getPassword());
//            Member member1 = memberRepository.save(member);
//            throw new Exception();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        transaction테스트
        Member member = new Member(memberRequestDTO.getName(), memberRequestDTO.getEmail(), memberRequestDTO.getPassword());
        Member member1 = memberRepository.save(member);

    }

    public MemberResponseDTO memberfindById(int memberid) throws EntityNotFoundException {  //ID 로 멤버 찾아서 반환
        Member member = memberRepository.findById(memberid).orElseThrow(()->new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        {
            MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
            memberResponseDTO.setId(member.getId());
            memberResponseDTO.setEmail(member.getEmail());
            memberResponseDTO.setName(member.getName());
            memberResponseDTO.setPassword(member.getPassword());
            memberResponseDTO.setCreate_time(member.getCreate_time());
            return memberResponseDTO;


        }


    }

    public void delete(int id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ;
        memberRepository.delete(member);

    }

    public void memberUpdate(MemberRequestDTO memberRequestDTO) {
        Member member = memberRepository.findById(memberRequestDTO.getId()).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberRequestDTO.getName(), memberRequestDTO.getPassword());
        memberRepository.save(member);
    }
}

