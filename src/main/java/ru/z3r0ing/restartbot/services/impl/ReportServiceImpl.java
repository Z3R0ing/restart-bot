package ru.z3r0ing.restartbot.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.z3r0ing.restartbot.data.entities.BotChat;
import ru.z3r0ing.restartbot.services.BotChatService;
import ru.z3r0ing.restartbot.services.HostsCheckerService;
import ru.z3r0ing.restartbot.services.ReportService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(ReportService.NAME)
public class ReportServiceImpl implements ReportService {

    @Autowired
    HostsCheckerService hostsCheckerService;
    @Autowired
    BotChatService botChatService;

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void notifySubscribedChatsAboutHostIsDown() {
        // Getting hosts statuses
        Map<String, Boolean> statuses;
        String report;
        try {
            statuses = hostsCheckerService.getHostsStatus();
        } catch (IOException ioe) {
            log.error("Exception while getting hosts statuses", ioe);
            return;
        }

        // Filter only hosts that are down
        Map<String, Boolean> badStatuses = new HashMap<>();
        statuses.forEach((name, status) -> {
            if (!status) badStatuses.put(name, status);
        });

        if (badStatuses.isEmpty()) {
            log.info("All hosts is up");
            return;
        }

        // Generating report message
        report = getHostsStatusMessage(badStatuses);

        // Sending report in subscribed chat
        List<BotChat> chats = botChatService.getSubscribedBotChats();
        chats.forEach(botChat -> {
            try {
                botChatService.sendMessageToChat(botChat.getId().toString(), report);
            } catch (TelegramApiException tae) {
                log.error("Error while sending message to chat", tae);
            }
        });
    }

    @Override
    public String getReportAboutHostsStatuses() {
        // Getting hosts statuses
        Map<String, Boolean> statuses;
        try {
            statuses = hostsCheckerService.getHostsStatus();
        } catch (IOException ioe) {
            log.error("Exception while getting hosts statuses", ioe);
            return "Something went wrong...";
        }

        // Generating report message
        return getHostsStatusMessage(statuses);
    }

    /**
     * Generates textual view of statuses
     * @param statuses hosts statuses map
     * @return string with multiline report
     */
    private String getHostsStatusMessage(Map<String, Boolean> statuses) {
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
