package net.trivia.demo.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TriviaDto {

    @JsonProperty("results")
    private List<QuestionAndAnswersDto> questionAndAnswersDtos;
}
