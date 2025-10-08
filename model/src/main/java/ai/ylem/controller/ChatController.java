package ai.ylem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * ChatController
 *
 * @since 2025/09/14
 **/
@Tag(name = "ChatController", description = "Open ai chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatModel chatModel;

    @GetMapping("/chat")
    public Mono<String> generate(
        @RequestParam(value = "message", defaultValue = "Hi! What can you do?") String message) {
        return Mono.fromCallable(() -> chatModel.call(message))
            .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> generateStream(@RequestParam String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}
