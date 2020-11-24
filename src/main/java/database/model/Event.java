package database.model;

import database.DBLiterals;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

import static database.DBLiterals.*;

@Entity
@Table(name = eventTable, schema = eventorSchema)
public class Event {
    @Id
    @PrimaryKeyJoinColumn
    @SequenceGenerator(name = eventIdSeq, schema = eventorSchema,
            sequenceName = eventIdSeq,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = eventIdSeq)
    private int id;
    @Column(name = DBLiterals.userId)
    private int userId;
    private String name;
    private String place;
    private LocalDateTime time;
    private String description;

    private int category;
    @ManyToOne
    @JoinColumn(name = DBLiterals.userId, insertable = false, updatable = false)
    private User user;

    @ManyToMany(mappedBy = subscribes)
    private Set<User> subscribers = new HashSet<>();

    public Event() {
    }

    public Event(String name, String place, LocalDateTime time, Category category, String description) {
        this.name = name;
        this.place = place;
        this.time = time;
        this.category = category.ordinal();
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }
    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId() == event.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(getName());
        joiner.add(getPlace());
        joiner.add(getTime().toString());
        joiner.add(getDescription());
        return joiner.toString();
    }
}
