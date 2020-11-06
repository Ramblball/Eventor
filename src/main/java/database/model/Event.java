package database.model;

import database.DBLiterals;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static database.DBLiterals.*;

@Entity
@Table(name = eventTable, schema = eventorSchema)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = DBLiterals.userId)
    private int userId;
    private String name;
    private String place;
    private LocalDateTime time;
    private String description;

    @Enumerated(EnumType.STRING)
    @Type(type = enumTypePostgreSQL)
    private Category category;
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
        this.category = category;
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

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
}
