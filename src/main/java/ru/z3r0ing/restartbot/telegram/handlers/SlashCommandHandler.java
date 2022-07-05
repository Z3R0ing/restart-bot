package ru.z3r0ing.restartbot.telegram.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Handler for slash commands
 */
public class SlashCommandHandler implements Handler {

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
            case "ping":
                if (commandWithPayload.length > 1) {
                    answerText = pingCommand(commandWithPayload[1]);
                } else {
                    answerText = "Enter host after command, and try again";
                }
                break;
            default:
                answerText = "Unknown command";
                break;
        }
        return new SendMessage(message.getChatId().toString(), answerText);
    }

    /**
     * Run ping command
     * @param host - host for ping
     * @return result of ping
     */
    String pingCommand(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            boolean reachable = address.isReachable(10000);
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

    @Override
    public boolean canWorkWithThisUpdate(Update update) {
        return !update.hasCallbackQuery() // without CallbackQuery
                && update.hasMessage() // with Message
                && update.getMessage().getText() != null // With text
                && update.getMessage().getText().startsWith("/"); // message text starts with '/'
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
