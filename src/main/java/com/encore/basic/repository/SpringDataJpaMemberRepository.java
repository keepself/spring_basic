package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
//spring data jpa의 기본기능을 쓰기 위해서는 JpaRepository를 상속해야함
//상속시에 entity명과 해당 entity의 pk타입을 명시
//실질적인 구현클래스와 스펙은 SimpleJpaRepository 클래스에 있고,
//실질적인 구동상황에서 hibernate구현체에 동작위임
public interface SpringDataJpaMemberRepository extends MemberRepository, JpaRepository<Member, Integer > {

}
