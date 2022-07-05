package ru.z3r0ing.restartbot.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.telegram.handlers.PlainHandler;

public class RestartBot extends TelegramLongPollingBot {

    @Autowired
    PlainHandler plainHandler;

    private String botUsername;
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null) {
            try {
                execute(plainHandler.handleMessage(update.getMessage()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
