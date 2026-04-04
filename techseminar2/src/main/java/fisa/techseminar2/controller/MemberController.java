package fisa.techseminar2.controller;

import fisa.techseminar2.domain.member.Member;
import fisa.techseminar2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequest request) {
        Member member = memberService.join(
                request.email(),
                request.password(),
                request.name(),
                request.age(),
                request.gender(),
                request.job(),
                request.monthlyBudget()
        );
        return ResponseEntity.ok("회원가입 완료: " + member.getEmail());
    }

    public record JoinRequest(
            String email,
            String password,
            String name,
            int age,
            String gender,
            String job,
            int monthlyBudget
    ) {}
}
