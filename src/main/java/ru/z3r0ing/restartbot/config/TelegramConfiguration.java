package ru.z3r0ing.restartbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.z3r0ing.restartbot.telegram.RestartBot;

@Configuration("restartbot_telegramConfiguration")
@PropertySource("classpath:telegram.properties")
public class TelegramConfiguration {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Bean(name = "restartbot_restartBot")
    public RestartBot restartBot() {
        RestartBot restartBot = new RestartBot();
        restartBot.setBotUsername(username);
        restartBot.setBotToken(token);
        return restartBot;
    }

}
