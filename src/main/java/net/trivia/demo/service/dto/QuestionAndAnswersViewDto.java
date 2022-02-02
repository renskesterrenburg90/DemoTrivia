package net.trivia.demo.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestionAndAnswersViewDto {

    private String question;
    private List<String> answers;
}
