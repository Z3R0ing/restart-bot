package ru.z3r0ing.restartbot.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Simple echo
 */
public class PlainMessageHandler implements Handler {

    @Override
    public BotApiMethod<?> handleUpdate(Update update) throws IllegalArgumentException {
        Message message = update.getMessage();
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }
        return new SendMessage(message.getChatId().toString(), message.getText());
    }

    @Override
    public boolean canWorkWithThisUpdate(Update update) {
        // if update get Message with text doesn't start with '/'
        return update.hasMessage() // with Message
                && update.getMessage().getText() != null // With text
                && !update.getMessage().getText().startsWith("/"); // message text doesn't start with '/'
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
