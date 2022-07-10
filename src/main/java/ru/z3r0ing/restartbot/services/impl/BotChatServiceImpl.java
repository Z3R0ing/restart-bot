package ru.z3r0ing.restartbot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import ru.z3r0ing.restartbot.data.entities.BotChat;
import ru.z3r0ing.restartbot.data.repos.BotChatRepository;
import ru.z3r0ing.restartbot.services.BotChatService;

import java.util.Optional;

@Service("restartbot_chatService")
public class BotChatServiceImpl implements BotChatService {

    @Autowired
    BotChatRepository botChatRepository;

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

}
