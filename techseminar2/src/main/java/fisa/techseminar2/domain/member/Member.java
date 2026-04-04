package fisa.techseminar2.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    // 비식별화 대상 필드
    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;      // M / F

    @Column
    private String job;

    @Column
    private int monthlyBudget;  // 월 보험료 예산
}
