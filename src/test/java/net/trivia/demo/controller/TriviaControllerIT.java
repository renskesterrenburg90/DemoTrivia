package net.trivia.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/questions")
                .session(mockHttpSession));

        //Then
        result.andExpect(status().isOk());

        //Given
        String json = "{\n" +
                "  \"questionsAndChosenAnswers\": [\n" +
                "    {\n" +
                "      \"question\": \"Question 1\",\n" +
                "      \"chosenAnswer\": \"chosen answer 1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"question\": \"question 2\",\n" +
                "      \"chosenAnswer\": \"chosen answer 2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"question\": \"question 3\",\n" +
                "      \"chosenAnswer\": \"chosen answer 3\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"question\": \"question 4\",\n" +
                "      \"chosenAnswer\": \"chosen answer 4\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"question\": \"question 5\",\n" +
                "      \"chosenAnswer\": \"chosen answer 5\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //When
        ResultActions postResults = mockMvc.perform(MockMvcRequestBuilders.post("/checkAnswers")
                .content(json)
                .session(mockHttpSession)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE));

        //Then
        postResults.andExpect(status().isOk());
    }
}
