package net.trivia.demo.service;

import net.trivia.demo.controller.dto.TriviaAnswers;
import net.trivia.demo.gateway.TriviaGateway;
import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.service.dto.QuestionsAndPossibleAnswers;
import net.trivia.demo.service.dto.TriviaQuestions;
import net.trivia.demo.service.results.TriviaResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.shuffle;
import static org.apache.commons.text.StringEscapeUtils.unescapeHtml4;

@Service
public class TriviaServiceImpl implements TriviaService {

    private final TriviaGateway triviaGateway;
    private final TriviaResults triviaResults = new TriviaResults();

    @Autowired
    public TriviaServiceImpl(TriviaGateway triviaGateway) {
        this.triviaGateway = triviaGateway;
    }

    @Override
    public TriviaQuestions getQuestionsAndPossibleAnswers() {
        List<QuestionAndAnswersDto> triviaQuestions = triviaGateway.getTriviaQuestions();
        List<QuestionAndAnswersDto> unescapedTriviaQuestions = unescapeQuestionsAndAnswers(triviaQuestions);
        setResults(unescapedTriviaQuestions);

        return new TriviaQuestions(shuffleAnswers(unescapedTriviaQuestions));
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

    private List<QuestionsAndPossibleAnswers> shuffleAnswers(List<QuestionAndAnswersDto> triviaQuestions) {
        ArrayList<QuestionsAndPossibleAnswers> questionAndAnswersViewDtos = new ArrayList<>();

        triviaQuestions.forEach(questionAndAnswersDto -> {
            ArrayList<String> answers = new ArrayList<>();
            answers.add(questionAndAnswersDto.getCorrectAnswer());
            answers.addAll(questionAndAnswersDto.getIncorrectAnswers());
            shuffle(answers);

            QuestionsAndPossibleAnswers questionAndAnswersViewDto = QuestionsAndPossibleAnswers.builder()
                    .question(questionAndAnswersDto.getQuestion())
                    .answers(answers)
                    .build();

            questionAndAnswersViewDtos.add(questionAndAnswersViewDto);
        });

        return questionAndAnswersViewDtos;
    }

    private void setResults(List<QuestionAndAnswersDto> triviaQuestions) {
        HashMap<String, String> questionsAndCorrectAnswers = new HashMap<>();
        triviaQuestions.forEach(questionAndAnswersDto -> {
            questionsAndCorrectAnswers.put(questionAndAnswersDto.getQuestion(), questionAndAnswersDto.getCorrectAnswer());
        });
        triviaResults.setQuestionAndCorrectAnswers(questionsAndCorrectAnswers);
    }

    @Override
    public List<Boolean> checkIfAnswersAreCorrect(TriviaAnswers triviaAnswers) {
        ArrayList<Boolean> results = new ArrayList<>();
        triviaAnswers.getQuestionsAndChosenAnswers().forEach(questionAndChosenAnswer -> {
            String correctAnswer = triviaResults.getQuestionAndCorrectAnswers().get(questionAndChosenAnswer.getQuestion());
            results.add(questionAndChosenAnswer.getChosenAnswer().equals(correctAnswer));
        });
        return results;
    }

    @Override
    public List<String> getCorrectAnswers() {
        return new ArrayList<>(triviaResults.getQuestionAndCorrectAnswers().values());
    }
}
