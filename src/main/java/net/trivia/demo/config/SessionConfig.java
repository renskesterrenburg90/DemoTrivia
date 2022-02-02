package net.trivia.demo.config;

import net.trivia.demo.gateway.TriviaGateway;
import net.trivia.demo.service.TriviaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class SessionConfig {

    @Bean
    @SessionScope
    public TriviaServiceImpl sessionScopedTriviaService(TriviaGateway triviaGateway) {
        return new TriviaServiceImpl(triviaGateway);
    }
}
