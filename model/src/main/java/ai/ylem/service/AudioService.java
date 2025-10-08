package ai.ylem.service;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.api.OpenAiAudioApi.TranscriptResponseFormat;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * AudioService
 *
 * @since 2025/10/04
 **/
@Service
@RequiredArgsConstructor
public class AudioService {

    private final TranscriptionModel openAiTranscriptionModel;

    private final OpenAiAudioSpeechModel openAiAudioSpeechModel;

    /**
     * 转录
     *
     * @param audioFile 音频文件
     * @return {@link AudioTranscriptionResponse }
     */
    public AudioTranscriptionResponse transcribe(Resource audioFile) {
        OpenAiAudioApi.TranscriptResponseFormat responseFormat = TranscriptResponseFormat.JSON;

        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
            .language("zh")
            // .prompt("Ask not this, but ask that")
            .temperature(0f).responseFormat(responseFormat).build();
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile,
            transcriptionOptions);
        return openAiTranscriptionModel.call(transcriptionRequest);
    }


    /**
     * TTS
     *
     * @param text 文本
     * @return {@link Flux }<{@link SpeechResponse }>
     */
    public Flux<SpeechResponse> tts(@NotBlank String text) {
        SpeechPrompt speechPrompt = new SpeechPrompt(text);
        return openAiAudioSpeechModel.stream(speechPrompt);
    }
}
