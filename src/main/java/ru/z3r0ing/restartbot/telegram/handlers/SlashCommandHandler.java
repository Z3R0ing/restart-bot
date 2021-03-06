package ru.z3r0ing.restartbot.telegram.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.z3r0ing.restartbot.data.entities.BotChat;
import ru.z3r0ing.restartbot.services.BotChatService;
import ru.z3r0ing.restartbot.services.ReportService;
import ru.z3r0ing.restartbot.utils.WebUtils;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Handler for slash commands
 */
public class SlashCommandHandler implements Handler {

    @Autowired
    BotChatService botChatService;
    @Autowired
    ReportService reportService;

    private static final Logger log = LoggerFactory.getLogger(SlashCommandHandler.class);

    @Override
    public BotApiMethod<?> handleUpdate(Update update) throws IllegalArgumentException {
        Message message = update.getMessage();
        String messageText = message.getText();

        // remove '/' in start of string and split: first element - command, second (if exist) - payload
        String[] commandWithPayload = messageText.substring(1).split(" ", 2);

        String answerText;
        // find by command
        switch (commandWithPayload[0]) {
            case "start":
                answerText = startCommand(message.getChat());
                break;
            case "ping":
                if (commandWithPayload.length > 1) {
                    answerText = pingCommand(commandWithPayload[1]);
                } else {
                    answerText = "Enter host after command, and try again";
                }
                break;
            case "status":
                answerText = statusCommand();
                break;
            case "notification":
                answerText = notificationCommand(message.getChat());
                break;
            default:
                answerText = "Unknown command";
                break;
        }

        // Creating answer message
        SendMessage answer = new SendMessage(message.getChatId().toString(), answerText);
        answer.setParseMode("MarkdownV2");
        return answer;
    }

    @Override
    public boolean canWorkWithThisUpdate(Update update) {
        // if update get Message with text starts with '/'
        return update.hasMessage() // with Message
                && update.getMessage().getText() != null // With text
                && update.getMessage().getText().startsWith("/"); // message text starts with '/'
    }

    @Override
    public int getPriority() {
        return 0;
    }

    /**
     * Registries new user if need
     * @param chat telegram chat instance
     * @return hello message
     */
    String startCommand(Chat chat) {
        BotChat botChat = botChatService.getBotChatByTelegramChat(chat);
        return "Hello, **" + botChat.getName() + "**\\!";
    }

    /**
     * Run ping command
     * @param host - host for ping
     * @return result of ping
     */
    String pingCommand(String host) {
        try {
            boolean reachable = WebUtils.isHostReachable(host);
            if (reachable) {
                return "Host " + host + " is up";
            } else {
                return "Host " + host + " is unreachable";
            }
        } catch (UnknownHostException uhe) {
            return "No IP address for the host could be found";
        } catch (IOException ioe) {
            log.error("Error while ping", ioe);
            return "Something went wrong...";
        }
    }

    private String statusCommand() {
        return reportService.getReportAboutHostsStatuses();
    }

    private String notificationCommand(Chat chat) {
        BotChat botChat = botChatService.getBotChatByTelegramChat(chat);
        if (botChat.getSubscribed() != null && botChat.getSubscribed()) {
            botChat.setSubscribed(false);
            botChatService.saveBotChat(botChat);
            return "You have unsubscribed from notification\\. You can subscribe by sending \"/notification\"";
        }
        else {
            botChat.setSubscribed(true);
            botChatService.saveBotChat(botChat);
            return "You have subscribed to notification\\. You can unsubscribe by sending \"/notification\"";
        }
    }
}
