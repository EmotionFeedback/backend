package capstone.example.EF;

import capstone.example.EF.dto.subject.request.ProfileSubjectRequestDto;
import capstone.example.EF.dto.subject.request.ScenarioSubjectRequestDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.RequestEntity.post;

public class CallApi {


    public static List<String> sendPostRequestToUrlWithHobby(String url, ProfileSubjectRequestDto requestDto) {

        WebClient webClient = WebClient.builder().build();

        // Request body parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        // requestDto에서 필요한 데이터를 가져와 requestBody에 추가
        // 예시: requestDto에서 name 필드를 가져와서 requestBody에 추가
        requestBody.add("job", requestDto.getJob());
        requestBody.add("hobby", requestDto.getHobby());

        // POST 요청 보내기
        Mono<ResponseEntity<List<String>>> responseMono = webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .toEntityList(String.class);

        ResponseEntity<List<String>> response = responseMono.block();

        List<String> responseList = Objects.requireNonNull(response).getBody();

        List<String> resultList = new ArrayList<>();
        for (String str : responseList) {
            String[] items = str.replace("[", "").replace("\"", "").split(",");

            resultList.addAll(Arrays.asList(items));
        }
        return resultList;

    }

    public static List<String> sendPostRequestToUrlWithContent(String url, ScenarioSubjectRequestDto requestDto) {

        WebClient webClient = WebClient.builder().build();

        // Request body parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        // requestDto에서 필요한 데이터를 가져와 requestBody에 추가
        // 예시: requestDto에서 name 필드를 가져와서 requestBody에 추가
        requestBody.add("content", requestDto.getContent());

        // POST 요청 보내기
        Mono<ResponseEntity<List<String>>> responseMono = webClient
                .post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .toEntityList(String.class);

        ResponseEntity<List<String>> response = responseMono.block();

        List<String> responseList = Objects.requireNonNull(response).getBody();

        List<String> resultList = new ArrayList<>();
        for (String str : responseList) {
            String[] items = str.replace("[", "").replace("\"", "").split(",");

            resultList.addAll(Arrays.asList(items));
        }

        return resultList;

    }
}
