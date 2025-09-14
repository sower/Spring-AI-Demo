package ai.ylem.chat;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * ChatController
 *
 * @since 2025/09/14
 **/
@Tag(name = "ChatController", description = "Open ai chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel chatModel;

    @GetMapping("/chat")
    public Map<String, String> generate(
        @RequestParam(value = "message", defaultValue = "Hi! What can you do?") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> generateStream(@RequestParam String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}
