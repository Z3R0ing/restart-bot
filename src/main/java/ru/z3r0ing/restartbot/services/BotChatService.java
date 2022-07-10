package ru.z3r0ing.restartbot.services;

import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.z3r0ing.restartbot.data.entities.BotChat;

public interface BotChatService {
    String NAME = "restartbot_chatService";

    /**
     * @param chat telegram chat instance
     * @return BotChat from DB
     * @apiNote Creates new record if BotChat with such ID not found in DB
     */
    BotChat getBotChatByTelegramChat(Chat chat);

}
