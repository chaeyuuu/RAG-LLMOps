package fisa.techseminar2.infrastructure.llm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class OllamaLlmAdapter implements LlmPort {

    private final ChatClient chatClient;

    @Value("${prompt.config.path:/app/config/system-prompt}")
    private String promptConfigPath;

    public OllamaLlmAdapter(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private String loadPrompt() {
        try {
            Path path = Paths.get(promptConfigPath);
            if (Files.exists(path)) {
                return Files.readString(path);
            }
        } catch (IOException e) {
            log.warn("프롬프트 파일 읽기 실패, 기본 프롬프트 사용: {}", e.getMessage());
        }
        // 기본 프롬프트 (로컬 개발용)
        return """
                당신은 보험 전문가입니다.
                아래 제공된 보험 상품 정보만을 기반으로 답변하세요.
                제공된 정보 외의 내용은 절대 추측하지 마세요.
                답변은 반드시 한국어로 작성하세요.
                """;
    }

    @Override
    public String generate(String prompt) {
        return chatClient
                .prompt()
                .system(loadPrompt())
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String generateWithContext(String prompt, String context) {
        String fullPrompt = """
                [보험 상품 정보]
                %s
                
                [질문]
                %s
                """.formatted(context, prompt);

        return chatClient
                .prompt()
                .system(loadPrompt())
                .user(fullPrompt)
                .call()
                .content();
    }
}
