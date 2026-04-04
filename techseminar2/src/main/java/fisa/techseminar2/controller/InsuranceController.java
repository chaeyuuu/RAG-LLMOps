package fisa.techseminar2.controller;

import fisa.techseminar2.domain.insurance.InsuranceProduct;
import fisa.techseminar2.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<InsuranceProduct>> findAll() {
        return ResponseEntity.ok(insuranceService.findAll());
    }

    // 조건 기반 필터링
    @GetMapping("/search")
    public ResponseEntity<List<InsuranceProduct>> search(
            @RequestParam int age,
            @RequestParam String category,
            @RequestParam int budget
    ) {
        return ResponseEntity.ok(
                insuranceService.findByCondition(age, category, budget)
        );
    }

    // 전체 상품 임베딩 (초기 데이터 세팅용)
    @PostMapping("/embed")
    public ResponseEntity<String> embed() {
        insuranceService.embedAllProducts();
        return ResponseEntity.ok("임베딩 완료");
    }
}
