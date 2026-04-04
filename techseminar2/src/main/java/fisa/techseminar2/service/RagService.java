package fisa.techseminar2.service;

import fisa.techseminar2.domain.chat.ChatMessage;
import fisa.techseminar2.domain.chat.ChatSession;
import fisa.techseminar2.infrastructure.llm.LlmPort;
import fisa.techseminar2.infrastructure.vectorstore.VectorStorePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RagService {

    private final LlmPort llmPort;
    private final VectorStorePort vectorStorePort;
    private final ChatService chatService;

    public String chat(ChatSession session, String userMessage) {

        // 1. 사용자 메시지 저장
        chatService.saveMessage(session, "user", userMessage);

        // 2. 벡터 검색 (유사 보험 상품 Top 3)
        List<String> relatedProducts = vectorStorePort.search(userMessage, 3);
        log.info("검색된 관련 상품 수: {}", relatedProducts.size());

        // 3. 대화 이력 조회 (멀티턴)
        List<ChatMessage> history = chatService.getRecentMessages(session.getId());
        String conversationHistory = history.stream()
                .map(m -> m.getRole() + ": " + m.getContent())
                .collect(Collectors.joining("\n"));

        // 4. 컨텍스트 조립
        String context = """
                [이전 대화]
                %s
                
                [관련 보험 상품]
                %s
                """.formatted(
                conversationHistory,
                String.join("\n---\n", relatedProducts)
        );

        // 5. LLM 호출
        String answer = llmPort.generateWithContext(userMessage, context);
        log.info("LLM 응답 완료");

        // 6. 응답 저장
        chatService.saveMessage(session, "assistant", answer);

        return answer;
    }
}