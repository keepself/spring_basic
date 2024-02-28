package com.encore.ordering.member.domain;

import com.encore.ordering.common.BaseTimeEntity;
import com.encore.ordering.member.dto.request.CreateMemberRequest;
import com.encore.ordering.order.domain.Ordering;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Ordering> orderings;

    public static Member toEntity(CreateMemberRequest createMemberRequest){
        Address address = Address.builder()
                .city(createMemberRequest.getCity())
                .street(createMemberRequest.getStreet())
                .zipcode(createMemberRequest.getZipcode())
                .build();
        return Member.builder()
                .name(createMemberRequest.getName())
                .email(createMemberRequest.getEmail())
                .password(createMemberRequest.getPassword())
                .address(address)
                .role(Role.USER)
                .build();
    }
}
