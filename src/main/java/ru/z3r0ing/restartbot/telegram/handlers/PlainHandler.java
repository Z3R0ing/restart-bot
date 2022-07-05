package ru.z3r0ing.restartbot.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Simple echo
 */
@Component("restartbot_plainHandler")
public class PlainHandler {

    public SendMessage handleMessage(Message message) {
        return new SendMessage(message.getChatId().toString(), message.getText());
    }

}
