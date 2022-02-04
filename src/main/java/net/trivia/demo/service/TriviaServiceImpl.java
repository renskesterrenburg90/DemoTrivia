package net.trivia.demo.service;

import net.trivia.demo.gateway.TriviaGateway;
import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.service.dto.QuestionAndAnswersViewDto;
import net.trivia.demo.service.results.TriviaResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;

@Service
public class TriviaServiceImpl implements TriviaService {

    private final TriviaGateway triviaGateway;
    private final TriviaResults triviaResults = new TriviaResults();

    @Autowired
    public TriviaServiceImpl(TriviaGateway triviaGateway) {
        this.triviaGateway = triviaGateway;
    }

    @Override
    public List<QuestionAndAnswersViewDto> getQuestionsAndPossibleAnswers() {
        List<QuestionAndAnswersDto> triviaQuestions = triviaGateway.getTriviaQuestions();
        setResults(triviaQuestions);

        return shuffleAnswers(triviaQuestions);
    }

    private List<QuestionAndAnswersViewDto> shuffleAnswers(List<QuestionAndAnswersDto> triviaQuestions) {
        return triviaQuestions.stream().map(questionAndAnswersDto -> {
                    ArrayList<String> answers = new ArrayList<>();
                    answers.add(questionAndAnswersDto.getCorrectAnswer());
                    answers.addAll(questionAndAnswersDto.getIncorrectAnswers());
                    shuffle(answers);

                    return QuestionAndAnswersViewDto.builder()
                            .question(questionAndAnswersDto.getQuestion())
                            .answers(answers)
                            .build();
                }).collect(Collectors.toList());
    }

    private void setResults(List<QuestionAndAnswersDto> triviaQuestions) {
        Map<String, String> questionsAndCorrectAnswers = triviaQuestions.stream()
                .collect(Collectors.toMap(QuestionAndAnswersDto::getQuestion, QuestionAndAnswersDto::getCorrectAnswer));

        triviaResults.setQuestionAndCorrectAnswers(questionsAndCorrectAnswers);
    }

    @Override
    public List<Boolean> checkIfAnswersAreCorrect(Map<String, String> questionsAndChosenAnswers) {
        return questionsAndChosenAnswers.entrySet().stream()
                .map(entry -> entry.getValue().equals(triviaResults.getQuestionAndCorrectAnswers().get(entry.getKey())))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> getCorrectAnswers() {
        return new HashMap<>(triviaResults.getQuestionAndCorrectAnswers());
    }
}
