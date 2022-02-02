package net.trivia.demo.controller;

import net.trivia.demo.service.TriviaServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TriviaController {
    @Resource(name = "sessionScopedTriviaService")
    TriviaServiceImpl triviaService;

    @GetMapping(value = {"/", "/questions"})
    public String getQuestionAndAnswers(final Model model) {
        model.addAttribute("triviaQuestions", triviaService.getQuestionsAndPossibleAnswers());
        return "index";
    }

    @PostMapping(value = "/checkAnswers")
    public String checkAnswers(final Model model, @RequestBody MultiValueMap<String, String> formData) {
        List<Boolean> results = triviaService.checkIfAnswersAreCorrect(formData.toSingleValueMap());
        model.addAttribute("triviaQuestions", formData.toSingleValueMap());
        model.addAttribute("results", results);
        model.addAttribute("correctAnswers", triviaService.getCorrectAnswers());
        return "results";
    }

}
