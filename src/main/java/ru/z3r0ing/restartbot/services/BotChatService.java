package ru.z3r0ing.restartbot.services;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.data.entities.BotChat;

import java.util.List;

public interface BotChatService {
    String NAME = "restartbot_chatService";

    /**
     * Saves a given BotChat entity
     * @param botChat BotChat entity
     * @return the saved BotChat entity
     */
    BotChat saveBotChat(BotChat botChat);

    /**
     * @param chat telegram chat instance
     * @return BotChat from DB
     * @apiNote Creates new record if BotChat with such ID not found in DB
     */
    BotChat getBotChatByTelegramChat(Chat chat);

    /**
     * @return list of subscribed BotChats
     */
    List<BotChat> getSubscribedBotChats();

    /**
     * @return list of not subscribed BotChats
     */
    List<BotChat> getUnsubscribedBotChats();

    /**
     * Sends message to chat with such ID
     * @param chatId telegram chat ID
     * @param message message text
     * @throws TelegramApiException if telegram API error
     */
    void sendMessageToChat(String chatId, String message) throws TelegramApiException;

}
