package net.trivia.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TriviaResults {
    private List<Boolean> results;
    private List<String> correctAnswers;
}
