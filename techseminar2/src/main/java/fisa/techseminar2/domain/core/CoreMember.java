package fisa.techseminar2.domain.core;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "core_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class CoreMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String realName;        // 실명

    @Column(nullable = false)
    private int age;                // 실제 나이

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int monthlyBudget;      // 실제 월 예산

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private String phoneNumber;     // 실제 전화번호

    @Column(nullable = false)
    private boolean isTransferred;  // 정보계 이관 여부
}