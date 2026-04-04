package fisa.techseminar2.controller;

import fisa.techseminar2.domain.chat.ChatSession;
import fisa.techseminar2.domain.member.Member;
import fisa.techseminar2.service.ChatService;
import fisa.techseminar2.service.MemberService;
import fisa.techseminar2.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final RagService ragService;
    private final ChatService chatService;
    private final MemberService memberService;

    // 새 세션 시작
    @PostMapping("/session")
    public ResponseEntity<String> createSession(@RequestParam String email) {
        Member member = memberService.findByEmail(email);
        ChatSession session = chatService.createSession(member);
        return ResponseEntity.ok(session.getSessionKey());
    }

    // 챗봇 질문
    @PostMapping
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        ChatSession session = chatService.findBySessionKey(request.sessionKey());
        String answer = ragService.chat(session, request.message());
        return ResponseEntity.ok(answer);
    }

    public record ChatRequest(
            String sessionKey,
            String message
    ) {
    }
}
