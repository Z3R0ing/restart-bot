package ru.z3r0ing.restartbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.z3r0ing.restartbot.telegram.handlers.PlainMessageHandler;
import ru.z3r0ing.restartbot.telegram.handlers.SlashCommandHandler;

@Configuration("restartbot_handlersConfiguration")
public class HandlersConfiguration {

    @Bean(name = "restartbot_plainMessageHandler")
    public PlainMessageHandler plainMessageHandler() {
        return new PlainMessageHandler();
    }

    @Bean(name = "restartbot_slashCommandHandler")
    public SlashCommandHandler slashCommandHandler() {
        return new SlashCommandHandler();
    }

}
