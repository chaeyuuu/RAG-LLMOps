package fisa.techseminar2.infrastructure.llm;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OllamaLlmAdapter implements LlmPort{

    private final ChatClient chatClient;

    @Override
    public String generate(String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String generateWithContext(String prompt, String context) {
        String fullPrompt = """
                아래 보험 상품 정보를 참고해서 답변해주세요.
                제공된 정보 외의 내용은 절대 추측하지 마세요.
                
                [보험 상품 정보]
                %s
                
                [질문]
                %s
                """.formatted(context, prompt);

        return chatClient
                .prompt()
                .user(fullPrompt)
                .call()
                .content();
    }
}
