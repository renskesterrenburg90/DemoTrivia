package net.trivia.demo.gateway;

import net.trivia.demo.gateway.dto.TriviaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@FeignClient(name = "Trivia", url = "${trivia.baseUrl}")
public interface TriviaApi {

    @GetMapping("/api.php?amount=5")
    Optional<TriviaDto> getTriviaQuestions();
}
