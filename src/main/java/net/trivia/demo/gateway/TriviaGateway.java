package net.trivia.demo.gateway;

import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.gateway.dto.TriviaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;

@Component
public class TriviaGateway {

    private final TriviaApi triviaApi;

    @Autowired
    public TriviaGateway(TriviaApi triviaApi) {
        this.triviaApi = triviaApi;
    }

    public List<QuestionAndAnswersDto> getTriviaQuestions() {
        Optional<TriviaDto> triviaQuestions = triviaApi.getTriviaQuestions();
        return unescapeQuestionsAndAnswers(triviaQuestions
                .map(TriviaDto::getQuestionAndAnswersDtos)
                .orElse(emptyList()));
    }

    private List<QuestionAndAnswersDto> unescapeQuestionsAndAnswers(List<QuestionAndAnswersDto> triviaQuestions) {
        List<QuestionAndAnswersDto> unescapedTriviaQuestions = triviaQuestions.stream().map(questionAndAnswersDto -> {
            questionAndAnswersDto.setQuestion(unescape(questionAndAnswersDto.getQuestion()));
            questionAndAnswersDto.setCorrectAnswer(unescape(questionAndAnswersDto.getCorrectAnswer()));
            questionAndAnswersDto.setIncorrectAnswers(unescapeAnswers(questionAndAnswersDto.getIncorrectAnswers()));
            return questionAndAnswersDto;
        }).collect(Collectors.toList());
        return unescapedTriviaQuestions;
    }

    private String unescape(String escapedString) {
        return unescapeHtml4(escapedString);
    }

    private List<String> unescapeAnswers(List<String> incorrectAnswers) {
        return incorrectAnswers.stream()
                .map(this::unescape)
                .collect(Collectors.toList());
    }
}
