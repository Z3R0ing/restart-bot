package ru.z3r0ing.restartbot.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.data.entities.BotChat;
import ru.z3r0ing.restartbot.data.repos.BotChatRepository;
import ru.z3r0ing.restartbot.services.BotChatService;
import ru.z3r0ing.restartbot.services.HostsCheckerService;
import ru.z3r0ing.restartbot.services.NotificationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service(NotificationService.NAME)
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    BotChatRepository botChatRepository;
    @Autowired
    HostsCheckerService hostsCheckerService;
    @Autowired
    BotChatService botChatService;

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void notifyAllChatsAboutHostStatus() {
        // Getting hosts statuses
        Map<String, Boolean> statuses;
        try {
             statuses = hostsCheckerService.getHostsStatus();
        } catch (IOException ioe) {
            log.error("Exception while getting hosts statuses", ioe);
            return;
        }

        // Generating report message
        String message = getHostsStatusMessage(statuses);

        // Sending report in all chat
        List<BotChat> chats = botChatRepository.findAll();
        chats.forEach(botChat -> {
            try {
                botChatService.sendMessageToChat(botChat.getId().toString(), message);
            } catch (TelegramApiException tae) {
                log.error("Error while sending message to chat", tae);
            }
        });
    }

    String getHostsStatusMessage(Map<String, Boolean> statuses) {
        StringBuilder messageBuilder = new StringBuilder("Hosts status:\n");
        for (Map.Entry<String, Boolean> entry : statuses.entrySet()) {
            String name = entry.getKey();
            Boolean status = entry.getValue();
            messageBuilder.append("**")
                    .append(name)
                    .append("**: ")
                    .append(status ? "is up" : "is down")
                    .append("\n");
        }
        return messageBuilder.toString();
    }

}
