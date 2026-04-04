package fisa.techseminar2.repository;

import fisa.techseminar2.domain.insurance.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Long> {

    // 카테고리로 필터링
    List<InsuranceProduct> findByCategoryAndIsActiveTrue(String category);

    // 나이 + 카테고리 + 예산 조건 필터링
    @Query("SELECT p FROM InsuranceProduct p WHERE " +
            "p.isActive = true AND " +
            "p.minAge <= :age AND p.maxAge >= :age AND " +
            "p.category = :category AND " +
            "p.minPremium <= :budget")
    List<InsuranceProduct> findByCondition(
            @Param("age") int age,
            @Param("category") String category,
            @Param("budget") int budget
    );

    // 활성 상품 전체 조회
    List<InsuranceProduct> findByIsActiveTrue();
}