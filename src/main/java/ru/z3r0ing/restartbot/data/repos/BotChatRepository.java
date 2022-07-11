package ru.z3r0ing.restartbot.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.z3r0ing.restartbot.data.entities.BotChat;

import java.util.List;

public interface BotChatRepository extends JpaRepository<BotChat, Long> {

    /**
     * @param isSubscribed subscribed or not subscribed boolean
     * @return all subscribed or not subscribed BotChats
     */
    @Query("select bc from restartbot_chat bc where bc.isSubscribed = :isSubscribed")
    List<BotChat> findBySubscribed(@Param("isSubscribed") boolean isSubscribed);

}
