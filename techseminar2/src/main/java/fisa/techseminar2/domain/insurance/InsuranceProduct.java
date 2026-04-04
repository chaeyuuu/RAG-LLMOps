package fisa.techseminar2.domain.insurance;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "insurance_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class InsuranceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String productCode;     // 상품 고유코드

    @Column(nullable = false)
    private String insurerName;     // 보험사명

    @Column(nullable = false)
    private String productName;     // 상품명

    @Column(nullable = false)
    private String category;        // 암 / 실손 / 종신 / 연금

    @Column(nullable = false)
    private int minPremium;         // 최소 보험료

    @Column(nullable = false)
    private int maxPremium;         // 최대 보험료

    @Column(nullable = false)
    private int minAge;             // 가입 최소 나이

    @Column(nullable = false)
    private int maxAge;             // 가입 최대 나이

    @Column(columnDefinition = "TEXT")
    private String description;     // 보장 내용 (임베딩 대상)

    @Column(nullable = false)
    private boolean isActive;       // 판매 중 여부
}