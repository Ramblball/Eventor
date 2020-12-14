package database.model;

import database.DBLiterals;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static database.DBLiterals.*;

@Entity
@Table(name = EVENT_TABLE, schema = EVENTOR_SCHEMA)
public class Event {
    @Id
    @PrimaryKeyJoinColumn
    @SequenceGenerator(name = EVENT_ID_SEQ, schema = EVENTOR_SCHEMA,
            sequenceName = EVENT_ID_SEQ,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = EVENT_ID_SEQ)
    private int id;
    @Column(name = DBLiterals.USER_ID)
    private int userId;
    private String name;
    private String place;
    private LocalDateTime time;
    private String description;

    private int category;
    @ManyToOne
    @JoinColumn(name = DBLiterals.USER_ID, insertable = false, updatable = false)
    private User user;

    @ManyToMany(mappedBy = SUBSCRIBES)
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

    public Integer getId() {
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
        return getId().equals(event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("<b>"+getName()+"</b>");
        joiner.add(getPlace());
        joiner.add("<a href=\"tg://user?id=" + getUserId() + "\">Создатель мероприятия</a>");
        joiner.add(getTime().toLocalDate().toString()+ " " +
                getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        joiner.add(getDescription());
        return joiner.toString();
    }
}