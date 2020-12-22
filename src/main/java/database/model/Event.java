package database.model;

import database.DBLiterals;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static database.DBLiterals.*;

@Entity
@FetchProfile(name = EVENT_WITH_SUBSCRIBERS, fetchOverrides = {
        @FetchProfile.FetchOverride(entity = Event.class, association = SUBSCRIBERS, mode = FetchMode.JOIN)
})
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
    @Column(name = LIMIT)
    private int limit;
    private float latitude;
    private float longitude;
    private int category;

    @ManyToOne
    @JoinColumn(name = DBLiterals.USER_ID, insertable = false, updatable = false)
    private User user;

    @ManyToMany(mappedBy = SUBSCRIBES)
    private Set<User> subscribers = new HashSet<>();

    public Event() {
    }

    public Event(String name, String place, Float lat, Float lng, Integer limit,
                 LocalDateTime time, Category category, String description) {
        this.name = name;
        this.place = place;
        this.latitude = lat;
        this.longitude = lng;
        this.limit = limit;
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

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
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
        joiner.add("<a href=\"tg://user?id=" + getUserId() + "\">Создатель мероприятия</a>");
        joiner.add(getTime().toLocalDate().toString()+ " " +
                getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        joiner.add("<a href=\"https://www.google.com/maps/place/" + getLatitude() + "," + getLongitude() + "\">Место мероприятия</a>");
        joiner.add("Осталось " + (getLimit() - getSubscribers().size()) + " мест");
        joiner.add("Осталось дней до начала: " + ChronoUnit.DAYS.between(LocalDateTime.now(), getTime()));
        joiner.add(getDescription());
        return joiner.toString();
    }
}