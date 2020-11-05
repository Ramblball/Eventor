package database.model;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Entity
@FetchProfile(name = "users_with_subscribes", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = "subscribes", mode = FetchMode.JOIN)
})
@FetchProfile(name = "user_with_created", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = User.class, association = "createdEvents", mode = FetchMode.JOIN)
})
@Table(name = "\"user\"", schema = "eventor_schema")
public class User {
    @Id
    @SequenceGenerator(name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq")
    private int id;
    private String name;
    private byte[] salt;
    private byte[] hash;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Event> createdEvents;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_events", schema = "eventor_schema",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    List<Event> subscribes;

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

    public void setPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            this.salt = new byte[16];
            random.nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            this.hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassword(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] checkedHash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            return java.util.Arrays.equals(this.hash, checkedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
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
}

