package net.trivia.demo.service.results;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TriviaResults {
    private Map<String, String> questionAndCorrectAnswers;

}
