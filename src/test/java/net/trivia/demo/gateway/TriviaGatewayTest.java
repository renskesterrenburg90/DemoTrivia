package net.trivia.demo.gateway;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.gateway.dto.TriviaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TriviaGatewayTest {

    @Mock
    private TriviaApi triviaApi;

    @InjectMocks
    private TriviaGateway triviaGateway;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        triviaGateway = new TriviaGateway(triviaApi);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void canGetTriviaQuestions() throws IOException {
        //Given
        TriviaDto triviaDto = mapper.readValue(new File("src/test/resources/triviaApiResponse.json"), TriviaDto.class);
        when(triviaApi.getTriviaQuestions()).thenReturn(Optional.of(triviaDto));

        //When
        List<QuestionAndAnswersDto> questionsAndPossibleAnswers = triviaGateway.getTriviaQuestions();

        //Then
        assertThat(questionsAndPossibleAnswers, hasSize(5));
        assertThat(questionsAndPossibleAnswers.get(0).getQuestion(), equalTo("Who wrote the 1967 horror novel &quot;Rosemary&#039;s Baby&quot;?"));
        assertThat(questionsAndPossibleAnswers.get(0).getCorrectAnswer(), equalTo("Ira Levin"));
        assertThat(questionsAndPossibleAnswers.get(0).getIncorrectAnswers(), containsInAnyOrder("Stephen King", "Robert Bloch", "Mary Shelley"));

        assertThat( questionsAndPossibleAnswers.get(1).getQuestion(), equalTo("Who wrote the children&#039;s story &quot;The Little Match Girl&quot;?"));
        assertThat(questionsAndPossibleAnswers.get(1).getCorrectAnswer(), equalTo("Hans Christian Andersen"));
        assertThat(questionsAndPossibleAnswers.get(1).getIncorrectAnswers(), containsInAnyOrder("Charles Dickens", "Lewis Carroll", "Oscar Wilde"));

        assertThat(questionsAndPossibleAnswers.get(2).getQuestion(), equalTo("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane&#039;s conquests?" ));
        assertThat(questionsAndPossibleAnswers.get(2).getCorrectAnswer(), equalTo("5%"));
        assertThat(questionsAndPossibleAnswers.get(2).getIncorrectAnswers(), containsInAnyOrder("1%", "3%", "&lt;1%"));

        assertThat(questionsAndPossibleAnswers.get(3).getQuestion(), equalTo("Pablo Picasso is one of the founding fathers of &quot;Cubism.&quot;" ));
        assertThat(questionsAndPossibleAnswers.get(3).getCorrectAnswer(), equalTo("True"));
        assertThat(questionsAndPossibleAnswers.get(3).getIncorrectAnswers(), containsInAnyOrder("False"));

        assertThat(questionsAndPossibleAnswers.get(4).getQuestion(), equalTo("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton." ));
        assertThat(questionsAndPossibleAnswers.get(4).getCorrectAnswer(), equalTo("False"));
        assertThat(questionsAndPossibleAnswers.get(4).getIncorrectAnswers(), containsInAnyOrder("True"));
    }
}
