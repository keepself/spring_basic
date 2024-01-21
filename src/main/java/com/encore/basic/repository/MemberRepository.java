package com.encore.basic.repository;

import com.encore.basic.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(int memberid);

    List<Member> findAll();

    Member save(Member member);

    void delete(Member member);
}
