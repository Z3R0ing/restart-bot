package ru.z3r0ing.restartbot.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.data.entities.BotChat;
import ru.z3r0ing.restartbot.data.repos.BotChatRepository;
import ru.z3r0ing.restartbot.services.BotChatService;
import ru.z3r0ing.restartbot.telegram.RestartBot;

import java.util.Optional;

@Service(BotChatService.NAME)
public class BotChatServiceImpl implements BotChatService {

    @Autowired
    BotChatRepository botChatRepository;
    @Autowired
    RestartBot restartBot;

    private static final Logger log = LoggerFactory.getLogger(BotChatServiceImpl.class);

    @Override
    public BotChat getBotChatByTelegramChat(Chat chat) {
        Long id = chat.getId();
        Optional<BotChat> optionalChat = botChatRepository.findById(id);
        if (optionalChat.isPresent()) {
            return optionalChat.get();
        } else {
            String name = chat.getUserName() != null ? chat.getUserName() : chat.getTitle();
            BotChat newBotChat = new BotChat(id, name);
            return botChatRepository.save(newBotChat);
        }
    }

    @Override
    public void sendMessageToChat(String chatId, String message) throws TelegramApiException {
        SendMessage newMessage = new SendMessage(chatId, message);
        newMessage.setParseMode("MarkdownV2");
        restartBot.execute(newMessage);
        // log sent message
        log.info("Sent message " + newMessage.getChatId() + ": " + newMessage.getText());
    }
}
