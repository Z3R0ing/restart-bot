package ru.z3r0ing.restartbot.services;

public interface ReportService {
    String NAME = "restartbot_reportService";

    /**
     * Sends a message to all chats about hosts that are down
     */
    void notifyAllChatsAboutHostIsDown();

    /**
     * Generates text report about hosts statuses
     * @return string with multiline report or error string
     */
    String getReportAboutHostsStatuses();
}
