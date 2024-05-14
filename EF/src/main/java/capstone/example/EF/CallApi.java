package capstone.example.EF;

import capstone.example.EF.dto.subject.request.ProfileSubjectRequestDto;
import capstone.example.EF.dto.subject.request.ScenarioSubjectRequestDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

import static org.springframework.http.RequestEntity.post;

public class CallApi {


    public static List<String> sendPostRequestToUrlWithHobby(String url, ProfileSubjectRequestDto requestDto) {

        WebClient webClient = WebClient.builder().build();

        return webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {}).block();

    }

    public static List<String> sendPostRequestToUrlWithContent(String url, ScenarioSubjectRequestDto requestDto) {

        WebClient webClient = WebClient.builder().build();

        return webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {}).block();

    }
}
