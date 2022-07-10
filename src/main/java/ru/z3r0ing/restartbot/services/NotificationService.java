package ru.z3r0ing.restartbot.services;

public interface NotificationService {
    String NAME = "restartbot_notificationService";

    void notifyAllChatsAboutHostStatus();
}
