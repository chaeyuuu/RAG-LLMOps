package fisa.techseminar2.infrastructure.llm;

public interface LlmPort {
    String generate(String prompt);
    String generateWithContext(String prompt, String context);
}
