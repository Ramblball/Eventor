package database.model;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.util.*;

import static database.DBLiterals.*;

@Entity
@FetchProfile(name = usersWithSubscribes, fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = subscribes, mode = FetchMode.JOIN)
})
@FetchProfile(name = userWithCreated, fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = createdEvents, mode = FetchMode.JOIN)
})
@Table(name = userTable, schema = eventorSchema)
public class User {
    @Id
    @SequenceGenerator(name = userIdSeq, schema = eventorSchema,
            sequenceName = userIdSeq,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = userIdSeq)
    private int id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = user, orphanRemoval = true)
    private List<Event> createdEvents = new LinkedList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = usersEventsTable, schema = eventorSchema,
            joinColumns = @JoinColumn(name = userId),
            inverseJoinColumns = @JoinColumn(name = eventId))
    List<Event> subscribes = new LinkedList<>();

    public User() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getCreatedEvents() {
        return createdEvents;
    }

    public void setCreatedEvents(List<Event> createdEvents) {
        this.createdEvents = createdEvents;
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

    public List<Event> getSubscribes() {
        return subscribes;
    }

    public void setSubscribes(List<Event> subscribes) {
        this.subscribes = subscribes;
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