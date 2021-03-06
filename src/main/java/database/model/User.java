package database.model;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.*;

import static database.DBLiterals.*;

@Entity
@FetchProfile(name = USERS_WITH_SUBSCRIBES, fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = SUBSCRIBES, mode = FetchMode.JOIN)
})
@FetchProfile(name = USER_WITH_CREATED, fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = CREATED_EVENTS, mode = FetchMode.JOIN)
})
@Table(name = USER_TABLE, schema = EVENTOR_SCHEMA)
public class User {
    @Id
    private int id;
    private String username;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = USER, orphanRemoval = true)
    private Set<Event> createdEvents = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = USERS_EVENTS_TABLE, schema = EVENTOR_SCHEMA,
            joinColumns = @JoinColumn(name = USER_ID),
            inverseJoinColumns = @JoinColumn(name = EVENT_ID))
    Set<Event> subscribes = new HashSet<>();

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Set<Event> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(Set<Event> subscribes) {
        this.subscribes = subscribes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCreatedEvent(Event event) {
        event.setUserId(this.id);
        event.setUser(this);
        this.getCreatedEvents().add(event);
    }

    public void removeCreatedEvent(Event event) {
        event.setUser(null);
        this.getCreatedEvents().remove(event);
    }

    public void addSubscribe(Event event) {
        this.getSubscribes().add(event);
        event.getSubscribers().add(this);
    }

    public void removeSubscribe(Event event) {
        this.getSubscribes().remove(event);
        event.getSubscribers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}