package ai.ylem.service;

import ai.ylem.util.ResourceUtils;
import ai.ylem.util.TextReadUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 矢量服务
 *
 * @since 2025/09/27
 **/
@Service
@RequiredArgsConstructor
public class VectorService {

    private final VectorStore vectorStore;

    private final static TokenTextSplitter TEXT_SPLITTER = new TokenTextSplitter();

    /**
     * 搜索
     *
     * @param query 查询
     * @return {@link List }<{@link Document }>
     */
    public List<Document> search(String query) {
        return vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(5)
            .similarityThreshold(0.3D)
            .build());
    }

    /**
     * 添加
     *
     * @param resourceUrl 资源 URL
     */
    public void add(String resourceUrl) {
        List<Document> documents;
        if (StringUtils.endsWithIgnoreCase(resourceUrl, ".md")) {
            documents = TextReadUtils.readMarkdown(ResourceUtils.getResource(resourceUrl));
        } else {
            documents = TextReadUtils.readText(ResourceUtils.getResource(resourceUrl));
        }
        List<Document> split = TEXT_SPLITTER.split(documents);
        vectorStore.add(split);
    }

}
