package ru.z3r0ing.restartbot.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.telegram.handlers.Handler;
import ru.z3r0ing.restartbot.telegram.handlers.SlashCommandHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestartBot extends TelegramLongPollingBot {

    @Autowired
    private ApplicationContext context;

    private static final Logger log = LoggerFactory.getLogger(SlashCommandHandler.class);

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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            log.info("Received message " + message.getChatId() + ": " + message.getText());
        }

        // get all handlers beans
        Map<String, Handler> allHandlers = context.getBeansOfType(Handler.class);
        // suitable handlers will be stored in list
        List<Handler> handlers = new ArrayList<>();
        // for every handler
        for (Map.Entry<String, Handler> entry : allHandlers.entrySet()) {
            Handler handler = entry.getValue();
            // if handler can work with this update
            if (handler.canWorkWithThisUpdate(update)) {
                // add it in suitable handlers list
                handlers.add(handler);
            }
        }
        // if not found any handler
        if (handlers.size() <= 0) {
            log.error("Can't found handler for " + update);
            return;
        }
        // if number of handlers more then 1
        if (handlers.size() > 1) {
            // sort descending priority
            handlers.sort((h1, h2) -> Integer.compare(h2.getPriority(), h1.getPriority()));
        }
        // take only one or highest priority handler and handle update
        BotApiMethod<?> method = handlers.get(0).handleUpdate(update);

        // execute result of handling
        try {
            execute(method);
        } catch (TelegramApiException tae) {
            log.error("Error while execute telegram method", tae);
        }

        // if result of handling is sending message
        if (method instanceof SendMessage) {
            SendMessage sendMessage = (SendMessage) method;
            // log sent message
            log.info("Sended message " + sendMessage.getChatId() + ": " + sendMessage.getText());
        }
    }
}
