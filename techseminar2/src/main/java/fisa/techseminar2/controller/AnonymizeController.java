package fisa.techseminar2.controller;

import fisa.techseminar2.service.AnonymizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anonymize")
@RequiredArgsConstructor
public class AnonymizeController {

    private final AnonymizeService anonymizeService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer() {
        anonymizeService.transferNow();
        return ResponseEntity.ok("비식별화 이관 완료");
    }
}
