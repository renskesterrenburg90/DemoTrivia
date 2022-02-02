package net.trivia.demo.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.trivia.demo.gateway.TriviaGateway;
import net.trivia.demo.gateway.dto.QuestionAndAnswersDto;
import net.trivia.demo.service.dto.QuestionAndAnswersViewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TriviaServiceImplTest {

    @Mock
    private TriviaGateway triviaGateway;

    @InjectMocks
    private TriviaServiceImpl triviaService;

    private final ObjectMapper mapper = new ObjectMapper();
    private List<QuestionAndAnswersDto> questionAndAnswersDtos;

    @BeforeEach
    public void setUp() {
        triviaService = new TriviaServiceImpl(triviaGateway);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        questionAndAnswersDtos = createQuestionAndAnswersDtos();
    }

    @Test
    public void canGetQuestionsAndPossibleAnswers() {
        //Given
        when(triviaGateway.getTriviaQuestions()).thenReturn(questionAndAnswersDtos);

        //When
        List<QuestionAndAnswersViewDto> questionsAndPossibleAnswers = triviaService.getQuestionsAndPossibleAnswers();

        //Then
        assertThat(questionsAndPossibleAnswers, hasSize(5));
        assertThat(questionsAndPossibleAnswers.get(0).getQuestion(), equalTo("Who wrote the 1967 horror novel \"Rosemary's Baby\"?"));
        assertThat(questionsAndPossibleAnswers.get(0).getAnswers(), containsInAnyOrder("Ira Levin", "Stephen King", "Robert Bloch", "Mary Shelley"));

        assertThat( questionsAndPossibleAnswers.get(1).getQuestion(), equalTo("Who wrote the children's story \"The Little Match Girl\"?"));
        assertThat(questionsAndPossibleAnswers.get(1).getAnswers(), containsInAnyOrder("Hans Christian Andersen", "Charles Dickens", "Lewis Carroll", "Oscar Wilde"));

        assertThat(questionsAndPossibleAnswers.get(2).getQuestion(), equalTo("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane's conquests?" ));
        assertThat(questionsAndPossibleAnswers.get(2).getAnswers(), containsInAnyOrder("5%", "1%", "3%", "<1%"));

        assertThat(questionsAndPossibleAnswers.get(3).getQuestion(), equalTo("Pablo Picasso is one of the founding fathers of \"Cubism.\"" ));
        assertThat(questionsAndPossibleAnswers.get(3).getAnswers(), containsInAnyOrder("True", "False"));

        assertThat(questionsAndPossibleAnswers.get(4).getQuestion(), equalTo("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton." ));
        assertThat(questionsAndPossibleAnswers.get(4).getAnswers(), containsInAnyOrder("True", "False"));
    }

    @Test
    public void canReturnTrueWhenAnswersAreCorrect() {
        //Given
        when(triviaGateway.getTriviaQuestions()).thenReturn(questionAndAnswersDtos);

        HashMap<String, String> correctAnswers = new HashMap<>();
        correctAnswers.put("Who wrote the 1967 horror novel \"Rosemary's Baby\"?", "Ira Levin");
        correctAnswers.put("Who wrote the children's story \"The Little Match Girl\"?", "Hans Christian Andersen");
        correctAnswers.put("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane's conquests?", "5%");
        correctAnswers.put("Pablo Picasso is one of the founding fathers of \"Cubism.\"", "True");
        correctAnswers.put("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton.", "False");

        //When
        triviaService.getQuestionsAndPossibleAnswers();
        List<Boolean> booleans = triviaService.checkIfAnswersAreCorrect(correctAnswers);

        //Then
        assertThat(booleans, not(hasItem(Boolean.FALSE)));
    }

    @Test
    public void canReturnFalseWhenAnswersAreIncorrect() {
        //Given
        when(triviaGateway.getTriviaQuestions()).thenReturn(questionAndAnswersDtos);

        HashMap<String, String> incorrectAnswers = new HashMap<>();
        incorrectAnswers.put("Who wrote the 1967 horror novel \"Rosemary's Baby\"?", "Robert Bloch");
        incorrectAnswers.put("Who wrote the children's story \"The Little Match Girl\"?", "Charles Dickens");
        incorrectAnswers.put("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane's conquests?", "1%");
        incorrectAnswers.put("Pablo Picasso is one of the founding fathers of \"Cubism.\"", "False");
        incorrectAnswers.put("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton.", "True");

        //When
        triviaService.getQuestionsAndPossibleAnswers();
        List<Boolean> booleans = triviaService.checkIfAnswersAreCorrect(incorrectAnswers);

        //Then
        assertThat(booleans, not(hasItem(Boolean.TRUE)));
    }

    @Test
    public void canReturnCorrectAnswers() {
        //Given
        when(triviaGateway.getTriviaQuestions()).thenReturn(questionAndAnswersDtos);

        HashMap<String, String> actualQuestionsAndCorrectAnswers = new HashMap<>();
        actualQuestionsAndCorrectAnswers.put("Who wrote the 1967 horror novel \"Rosemary's Baby\"?", "Ira Levin");
        actualQuestionsAndCorrectAnswers.put("Who wrote the children's story \"The Little Match Girl\"?", "Hans Christian Andersen");
        actualQuestionsAndCorrectAnswers.put("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane's conquests?", "5%");
        actualQuestionsAndCorrectAnswers.put("Pablo Picasso is one of the founding fathers of \"Cubism.\"", "True");
        actualQuestionsAndCorrectAnswers.put("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton.", "False");

        //When
        triviaService.getQuestionsAndPossibleAnswers();
        Map<String, String> expectedCorrectAnswers = triviaService.getCorrectAnswers();

        //Then
        for (Map.Entry<String, String> actualQuestionAndCorrectAnswer : actualQuestionsAndCorrectAnswers.entrySet()) {
            assertThat(expectedCorrectAnswers.get(actualQuestionAndCorrectAnswer.getKey()), equalTo(actualQuestionAndCorrectAnswer.getValue()));
        }
    }

    private List<QuestionAndAnswersDto> createQuestionAndAnswersDtos() {
        ArrayList<QuestionAndAnswersDto> questionAndAnswersDtos = new ArrayList<>();
        questionAndAnswersDtos.add(getQuestionAndAnswersDto("Who wrote the 1967 horror novel \"Rosemary's Baby\"?", "Ira Levin", "Stephen King", "Robert Bloch", "Mary Shelley"));
        questionAndAnswersDtos.add(getQuestionAndAnswersDto("Who wrote the children's story \"The Little Match Girl\"?", "Hans Christian Andersen", "Charles Dickens", "Lewis Carroll", "Oscar Wilde"));
        questionAndAnswersDtos.add(getQuestionAndAnswersDto("According to scholarly estimates, what percentage of the world population at the time died due to Tamerlane's conquests?", "5%", "1%", "3%", "<1%"));
        questionAndAnswersDtos.add(getQuestionAndAnswersDto("Pablo Picasso is one of the founding fathers of \"Cubism.\"", "True", "False", "", ""));
        questionAndAnswersDtos.add(getQuestionAndAnswersDto("George Clinton, Vice President of the United States (1805-1812), is an ancestor of President Bill Clinton.", "False", "True", "", ""));
        return questionAndAnswersDtos;
    }

    private QuestionAndAnswersDto getQuestionAndAnswersDto(String question, String correctAnswer, String firstIncorrectAnswer, String secondIncorrectAnswer, String thirdIncorrectAnswer) {
        List<String> incorrectAnswers = Stream.of(firstIncorrectAnswer, secondIncorrectAnswer, thirdIncorrectAnswer)
                .filter(incorrectAnswer -> !StringUtils.isBlank(incorrectAnswer))
                .collect(Collectors.toList());
        return QuestionAndAnswersDto.builder()
                .question(question)
                .correctAnswer(correctAnswer)
                .incorrectAnswers(incorrectAnswers)
                .build();
    }
}
