package net.trivia.demo.service;

import net.trivia.demo.service.dto.QuestionAndAnswersViewDto;

import java.util.List;
import java.util.Map;

public interface TriviaService {

    List<QuestionAndAnswersViewDto> getQuestionsAndPossibleAnswers();

    List<Boolean> checkIfAnswersAreCorrect(Map<String, String> questionsAndChosenAnswers);

    Map<String, String> getCorrectAnswers();
}
