package fisa.techseminar2.service;

import fisa.techseminar2.domain.chat.ChatMessage;
import fisa.techseminar2.domain.chat.ChatSession;
import fisa.techseminar2.domain.member.Member;
import fisa.techseminar2.repository.ChatMessageRepository;
import fisa.techseminar2.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // 새 세션 생성
    @Transactional
    public ChatSession createSession(Member member) {
        String sessionKey = UUID.randomUUID().toString();

        // Redis에 세션 저장 (TTL 30분)
        redisTemplate.opsForValue().set(
                "session:" + sessionKey,
                member.getId().toString(),
                30,
                TimeUnit.MINUTES
        );

        ChatSession session = ChatSession.builder()
                .member(member)
                .sessionKey(sessionKey)
                .build();

        return chatSessionRepository.save(session);
    }

    // 메시지 저장
    @Transactional
    public ChatMessage saveMessage(ChatSession session, String role, String content) {
        ChatMessage message = ChatMessage.builder()
                .session(session)
                .role(role)
                .content(content)
                .build();

        return chatMessageRepository.save(message);
    }

    // 최근 대화 이력 조회 (멀티턴용)
    public List<ChatMessage> getRecentMessages(Long sessionId) {
        return chatMessageRepository
                .findTop10BySessionIdOrderByCreatedAtDesc(sessionId);
    }

    // 세션 조회
    public ChatSession findBySessionKey(String sessionKey) {
        return chatSessionRepository.findBySessionKey(sessionKey)
                .orElseThrow(() -> new IllegalArgumentException("세션이 존재하지 않습니다."));
    }
}
