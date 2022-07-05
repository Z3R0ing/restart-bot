package ru.z3r0ing.restartbot.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.Nullable;

/**
 * Interface for update handlers
 */
public interface Handler {

    /**
     * Handles received update
     * @param update received update
     * @return response or null if nothing to response
     * @throws IllegalArgumentException if handler can't work with such update
     * @apiNote should use updates return true from {@link #canWorkWithThisUpdate(Update)}
     */
    @Nullable
    BotApiMethod<?> handleUpdate(Update update) throws IllegalArgumentException;

    /**
     * Can handler work with such update
     * @param update received update
     * @return true if can
     */
    boolean canWorkWithThisUpdate(Update update);

    /**
     * If more then one handler can work with such update, it will be chosen with the most high priority
     * @return priority of choose this handler
     */
    int getPriority();

}
