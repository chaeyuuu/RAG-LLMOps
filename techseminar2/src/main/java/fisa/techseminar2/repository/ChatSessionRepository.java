package fisa.techseminar2.repository;

import fisa.techseminar2.domain.chat.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    Optional<ChatSession> findBySessionKey(String sessionKey);
    Optional<ChatSession> findTopByMemberIdOrderByCreatedAtDesc(Long memberId);
}
