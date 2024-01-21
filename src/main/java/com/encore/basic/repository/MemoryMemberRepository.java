package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    //    우리는 MemberRepository 인터페이스를 인플리먼츠해서 MemoryMemberRepository구현할건데
//    memorydb를 사용하기위해 List<Member>를 타입으로한 memberDB를 변수를선언했다.
//    memorydb란 List<Member>자체
    private final List<Member> memberDB;
    static int total_id;

    public MemoryMemberRepository() {
        this.memberDB = new ArrayList<>();
    }

    @Override
    public Optional<Member> findById(int memberid) {
//        memberDB list에서 member를 하나씩 꺼낸다
//        @Getter 어노테이션을 사용해서 Member타입인 변수 member 에서 id 를 꺼내려면
//        member.getid()
        //ember 변수를 null로 선언합니다. 이 변수는 나중에 memberDB에서 찾은 특정 멤버를 저장할 것입니다.
        for (Member member2 : memberDB) {
            if (member2.getId() == memberid) {
                return Optional.of(member2);

            }
        }

        return Optional.empty();
    }

    //add해서 쌓인 list<member>타입 memberDB를 반환
    @Override
    public List<Member> findAll() {
        return memberDB;
    }

    @Override
    public Member save(Member member) {

        total_id += 1;
        LocalDateTime now = LocalDateTime.now();
        member.setId(total_id);
        member.setCreate_time(now);
        this.memberDB.add(member);
        return member;
    }

    @Override
    public void delete(Member member) {
    }


}

