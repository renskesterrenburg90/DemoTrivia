package net.trivia.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TriviaAnswers {
    private List<QuestionAndChosenAnswers> questionsAndChosenAnswers;
}
