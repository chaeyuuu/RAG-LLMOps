package fisa.techseminar2.repository;

import fisa.techseminar2.domain.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 세션별 메시지 조회 (시간순)
    List<ChatMessage> findBySessionIdOrderByCreatedAtAsc(Long sessionId);

    // 최근 N개 메시지 조회 (멀티턴 컨텍스트용)
    List<ChatMessage> findTop10BySessionIdOrderByCreatedAtDesc(Long sessionId);
}