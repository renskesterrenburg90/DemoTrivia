package net.trivia.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TriviaQuestions {
    private List<QuestionsAndPossibleAnswers> questionsAndPossibleAnswers;
}
