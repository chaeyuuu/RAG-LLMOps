package fisa.techseminar2.infrastructure.vectorstore;

import java.util.List;

public interface VectorStorePort {
    void store(String id, String content);
    List<String> search(String query, int topK);
}
