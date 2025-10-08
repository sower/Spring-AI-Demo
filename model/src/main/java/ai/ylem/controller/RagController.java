package ai.ylem.controller;

import ai.ylem.service.RagService;
import ai.ylem.service.VectorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import reactor.core.scheduler.Schedulers;

/**
 * RAG控制器
 *
 * @since 2025/09/27
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class RagController {

    private final VectorService vectorService;

    private final RagService ragService;


    @PostMapping("/add")
    public Mono<ResponseEntity<String>> add(@RequestParam String resourceUrl) {
        return Mono.fromRunnable(() -> vectorService.add(resourceUrl))
            .subscribeOn(Schedulers.boundedElastic())
            .thenReturn(ResponseEntity.ok("Resource added successfully"))
            .onErrorReturn(ResponseEntity.internalServerError().body("Failed to add resource"));
    }

    @PostMapping("/batch-add")
    public Mono<ResponseEntity<String>> batchAdd(@RequestBody List<String> resourceUrls) {
        return Flux.fromIterable(resourceUrls).parallel().runOn(Schedulers.boundedElastic())
            .doOnNext(url -> {
                try {
                    vectorService.add(url);
                } catch (Exception e) {
                    log.error("Failed to add resource: {}", url, e);
                }
            }).sequential().then(Mono.just(ResponseEntity.ok("Batch processing completed")))
            .onErrorReturn(ResponseEntity.internalServerError().body("Failed to process batch"));
    }

    @GetMapping("/search")
    public Mono<List<Document>> search(@RequestParam(value = "query") String query) {
        return Mono.fromCallable(() -> vectorService.search(query))
            .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/rag")
    public Flux<ChatClientResponse> rag(@RequestParam(value = "query") String query) {
        return ragService.response(query);
    }

}