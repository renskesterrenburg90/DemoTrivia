package net.trivia.demo.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class QuestionsAndPossibleAnswers {
    private String question;
    private List<String> answers;
}
