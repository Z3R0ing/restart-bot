package ru.z3r0ing.restartbot.data.entities;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "restartbot_chat")
@Table(name = "RESTARTBOT_CHAT")
public class BotChat implements Serializable {
    private static final long serialVersionUID = 8699861054225570126L;

    public BotChat() {
    }

    public BotChat(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(name = "ID", nullable = false)
    Long id;

    @Column(name = "NAME")
    String name;

    @Column(name = "IS_SUBSCRIBED")
    Boolean isSubscribed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BotChat)) return false;
        BotChat botChat = (BotChat) o;
        return id.equals(botChat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
