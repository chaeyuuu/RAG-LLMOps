package fisa.techseminar2.service;

import fisa.techseminar2.domain.insurance.InsuranceProduct;
import fisa.techseminar2.infrastructure.vectorstore.VectorStorePort;
import fisa.techseminar2.repository.InsuranceProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InsuranceService {

    private final InsuranceProductRepository insuranceProductRepository;
    private final VectorStorePort vectorStorePort;

    // 조건 기반 필터링
    public List<InsuranceProduct> findByCondition(int age, String category, int budget) {
        return insuranceProductRepository.findByCondition(age, category, budget);
    }

    // 전체 상품 조회
    public List<InsuranceProduct> findAll() {
        return insuranceProductRepository.findByIsActiveTrue();
    }

    // 앱 시작 시 전체 상품 임베딩 → pgvector 저장
    @Transactional
    public void embedAllProducts() {
        List<InsuranceProduct> products = insuranceProductRepository.findByIsActiveTrue();

        products.forEach(product -> {
            String content = """
                    보험사: %s
                    상품명: %s
                    종류: %s
                    보험료: %d원 ~ %d원
                    가입나이: %d세 ~ %d세
                    보장내용: %s
                    """.formatted(
                    product.getInsurerName(),
                    product.getProductName(),
                    product.getCategory(),
                    product.getMinPremium(),
                    product.getMaxPremium(),
                    product.getMinAge(),
                    product.getMaxAge(),
                    product.getDescription()
            );

            vectorStorePort.store(
                    product.getProductCode(),
                    content
            );
            log.info("임베딩 완료: {}", product.getProductName());
        });
    }
}