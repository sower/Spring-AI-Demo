package ai.ylem.util;

import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;

/**
 * 文本读取实用程序
 *
 * @since 2025/09/27
 **/
public abstract class TextReadUtils {

    /**
     * 阅读文本
     *
     * @param resource 资源
     * @return {@link List }<{@link Document }>
     */
    public static List<Document> readText(Resource resource) {
        TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().putAll(ResourceUtils.getMetadata(resource));
        return textReader.read();
    }

    /**
     * 读取 JSON
     *
     * @param resource 资源
     * @return {@link List }<{@link Document }>
     */
    public static List<Document> readJson(Resource resource) {
        JsonReader jsonReader = new JsonReader(resource, "description", "content");
        return jsonReader.read();
    }

    /**
     * 读取 Markdown
     *
     * @param resource 资源
     * @return {@link List }<{@link Document }>
     */
    public static List<Document> readMarkdown(Resource resource) {
        MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
            .withHorizontalRuleCreateDocument(true)
            .withIncludeCodeBlock(true)
            .withIncludeBlockquote(true)
            .withAdditionalMetadata(ResourceUtils.getMetadata(resource))
            .build();
        MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
        return reader.get();
    }

}
