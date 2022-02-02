package net.trivia.demo.gateway;

import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.gateway.dto.TriviaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class TriviaGateway {

    private final TriviaApi triviaApi;

    @Autowired
    public TriviaGateway(TriviaApi triviaApi) {
        this.triviaApi = triviaApi;
    }

    public List<QuestionAndAnswersDto> getTriviaQuestions() {
        Optional<TriviaDto> triviaQuestions = triviaApi.getTriviaQuestions();
        return triviaQuestions
                .map(TriviaDto::getQuestionAndAnswersDtos)
                .orElse(emptyList());
    }
}
