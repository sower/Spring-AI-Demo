package ai.ylem.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * EmbedController
 *
 * @since 2025/09/27
 **/
@RestController
@RequiredArgsConstructor
public class EmbedController {

    private final EmbeddingModel embeddingModel;

    @GetMapping("/embedding")
    public Mono<EmbeddingResponse> embed(@RequestParam(value = "message") String message) {
        return Mono.fromCallable(() -> this.embeddingModel.embedForResponse(List.of(message)))
                .subscribeOn(Schedulers.boundedElastic());
    }

}