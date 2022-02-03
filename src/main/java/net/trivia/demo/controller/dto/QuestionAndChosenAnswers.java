package net.trivia.demo.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndChosenAnswers {
    private String question;
    private String chosenAnswer;
}
