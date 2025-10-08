package ai.ylem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * RagService
 *
 * @since 2025/09/29
 **/
@Component
@RequiredArgsConstructor
public class RagService {

    private final VectorStore vectorStore;

    private final OpenAiChatModel chatModel;

    PromptTemplate ragPromptTemplate = PromptTemplate.builder().renderer(
            StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
        .template("""
            你是一个知识库问答助手，能够根据提供的本地知识库内容回答问题。
            
            检索到的相关文档片段如下：
            ---
            <question_answer_context>
            ---
            
            要求：
            1. 回答必须严格基于参考内容，不要编造；
            2. 如果参考资料不足，请明确说明“资料不足，无法完整回答”；
            3. 引用时请标注对应的文档片段；
            4. 回答保持简洁、专业。
            
            用户问题：<query>
            """).build();

    public Flux<ChatClientResponse> response(String input) {
        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
            .promptTemplate(ragPromptTemplate)
            .searchRequest(SearchRequest.builder().similarityThreshold(0.6d).topK(5).build())
            .build();
        return ChatClient.builder(chatModel).build().prompt().advisors(qaAdvisor).user(input)
            .stream().chatClientResponse();
    }

}
