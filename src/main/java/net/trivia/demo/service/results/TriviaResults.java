package net.trivia.demo.service.results;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class TriviaResults {
    private HashMap<String, String> questionAndCorrectAnswers;

}
