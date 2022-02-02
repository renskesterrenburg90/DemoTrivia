package net.trivia.demo.service;

import net.trivia.demo.controller.dto.TriviaAnswers;
import net.trivia.demo.service.dto.TriviaQuestions;

import java.util.List;
import java.util.Map;

public interface TriviaService {

    TriviaQuestions getQuestionsAndPossibleAnswers();

    List<Boolean> checkIfAnswersAreCorrect(TriviaAnswers triviaAnswers);

    List<String> getCorrectAnswers();
}
