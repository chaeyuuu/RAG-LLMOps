package fisa.techseminar2.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "anonymized_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class AnonymizedMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String anonymizedId;    // HASH 처리된 ID

    @Column(nullable = false)
    private String ageGroup;        // 30대, 40대

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String budgetRange;     // 3~5만원

    @Column(nullable = false)
    private String job;
}