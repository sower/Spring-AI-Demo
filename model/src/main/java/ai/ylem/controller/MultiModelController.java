package ai.ylem.controller;

import ai.ylem.service.AudioService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 多模态控制器
 *
 * @since 2025/09/29
 **/
@Validated
@RestController
@RequiredArgsConstructor
public class MultiModelController {

    private final ImageModel imageModel;

    private final AudioService audioService;

    @GetMapping("/image")
    public Mono<ImageResponse> image(
        @RequestParam(value = "text", defaultValue = "A light cream colored mini golden doodle") String text) {
        return Mono.just(imageModel.call(new ImagePrompt(text,
                OpenAiImageOptions.builder().quality("hd").height(1024).width(1024).build())))
            .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<AudioTranscriptionResponse> transcribe(@RequestPart(value = "file") Resource audioFile) {
        return Mono.just(audioService.transcribe(audioFile))
            .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(value = "/tts")
    public Flux<SpeechResponse> tts(@NotBlank @RequestBody String text) {
        return audioService.tts(text);
    }

}
