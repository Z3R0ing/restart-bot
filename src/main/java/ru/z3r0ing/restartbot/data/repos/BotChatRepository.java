package ru.z3r0ing.restartbot.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.z3r0ing.restartbot.data.entities.BotChat;

public interface BotChatRepository extends JpaRepository<BotChat, Long> {
}
