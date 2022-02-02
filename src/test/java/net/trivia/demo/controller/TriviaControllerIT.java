package net.trivia.demo.controller;

import net.trivia.demo.service.dto.QuestionAndAnswersViewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration
class TriviaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTriviaQuestions() throws Exception {
        //Given
        MockHttpSession mockHttpSession = new MockHttpSession();

        //When
        MvcResult questionAndAnswers = mockMvc.perform(MockMvcRequestBuilders.get("/questions")
                .session(mockHttpSession))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<QuestionAndAnswersViewDto> triviaQuestions = (List<QuestionAndAnswersViewDto>) questionAndAnswers.getModelAndView().getModel().get("triviaQuestions");
        assertThat(triviaQuestions, hasSize(5));
        assertThat(triviaQuestions.get(0).getQuestion(), instanceOf(String.class));
        assertThat(triviaQuestions.get(0).getAnswers(), instanceOf(List.class));
        assertThat(triviaQuestions.get(0).getAnswers(), anyOf(hasSize(2), hasSize(4)));

        //Given
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add(triviaQuestions.get(0).getQuestion(), triviaQuestions.get(0).getAnswers().get(1));
        request.add(triviaQuestions.get(1).getQuestion(), triviaQuestions.get(1).getAnswers().get(0));
        request.add(triviaQuestions.get(2).getQuestion(), triviaQuestions.get(2).getAnswers().get(0));
        request.add(triviaQuestions.get(3).getQuestion(), triviaQuestions.get(3).getAnswers().get(1));
        request.add(triviaQuestions.get(4).getQuestion(), triviaQuestions.get(4).getAnswers().get(1));

        //When
        MvcResult results = mockMvc.perform(MockMvcRequestBuilders.post("/checkAnswers")
                .params(request)
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        List<Boolean> resultsList = (List<Boolean>) results.getModelAndView().getModel().get("results");
        Map<String, String> correctAnswers = (Map<String, String>) results.getModelAndView().getModel().get("correctAnswers");

        assertThat(resultsList, hasSize(5));
        assertThat(resultsList, anyOf(hasItem(Boolean.FALSE), hasItem(Boolean.TRUE)));

        assertThat(correctAnswers, hasKey(triviaQuestions.get(0).getQuestion()));
        assertThat(correctAnswers, hasKey(triviaQuestions.get(1).getQuestion()));
        assertThat(correctAnswers, hasKey(triviaQuestions.get(2).getQuestion()));
        assertThat(correctAnswers, hasKey(triviaQuestions.get(3).getQuestion()));
        assertThat(correctAnswers, hasKey(triviaQuestions.get(4).getQuestion()));
    }
}
