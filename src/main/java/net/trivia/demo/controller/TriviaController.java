package net.trivia.demo.controller;

import net.trivia.demo.controller.dto.TriviaAnswers;
import net.trivia.demo.service.TriviaServiceImpl;
import net.trivia.demo.service.dto.TriviaQuestions;
import net.trivia.demo.service.dto.TriviaResults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TriviaController {
    @Resource(name = "sessionScopedTriviaService")
    TriviaServiceImpl triviaService;

    @GetMapping(value = {"/", "/questions"})
    public TriviaQuestions getQuestionAndAnswers() {
        return triviaService.getQuestionsAndPossibleAnswers();
    }

    @PostMapping(value = "/checkAnswers")
    public TriviaResults checkAnswers(@RequestBody TriviaAnswers triviaAnswers) {
        return new TriviaResults(triviaService.checkIfAnswersAreCorrect(triviaAnswers), triviaService.getCorrectAnswers());
    }

}
