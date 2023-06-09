package ru.tinkoff.edu.java.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.client.interfaces.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.dto.response.StackOverflowQuestionsListResponse;

public class StackOverflowClientImpl implements StackOverflowClient {
    private static final String STACKOVERFLOW_URI = "/questions/{id}";
    private final String url;
    private WebClient stackoverflowWebClient;

    public StackOverflowClientImpl(String url) {
        this.url = url;
    }

    @PostConstruct
    public void buildStackoverflowWebClient() {
        stackoverflowWebClient = WebClient.builder()
            .baseUrl(url)
            .build();
    }

    @Override
    public Mono<StackOverflowQuestionResponse> fetchQuestion(long questionId) {
        return stackoverflowWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(STACKOVERFLOW_URI)
                .queryParam("site", "stackoverflow")
                .build(questionId))
            .retrieve()
            .bodyToMono(StackOverflowQuestionsListResponse.class)
            .flatMap(wrapper -> {
                if (wrapper.items().isEmpty()) {
                    return Mono.empty();
                }
                return Mono.just(wrapper.items().get(0));
            });
    }
}
