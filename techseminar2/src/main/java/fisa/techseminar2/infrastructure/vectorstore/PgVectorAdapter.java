package fisa.techseminar2.infrastructure.vectorstore;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PgVectorAdapter implements VectorStorePort {

    private final PgVectorStore vectorStore;

    @Override
    public void store(String id, String content) {
        Document document = new Document(content, Map.of("id", id));
        vectorStore.add(List.of(document));
    }

    @Override
    public List<String> search(String query, int topK) {
        return vectorStore
                .similaritySearch(
                        SearchRequest.builder()
                                .query(query)
                                .topK(topK)
                                .build()
                )
                .stream()
                .map(Document::getFormattedContent)
                .toList();
    }
}