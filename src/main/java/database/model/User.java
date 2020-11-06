package database.model;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

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
    @SequenceGenerator(name = userIdSeq,
            sequenceName = userIdSeq,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = userIdSeq)
    private int id;
    private String name;
    private byte[] salt;
    private byte[] hash;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = user, orphanRemoval = true)
    private List<Event> createdEvents;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = usersEvents, schema = eventorSchema,
            joinColumns = @JoinColumn(name = userId),
            inverseJoinColumns = @JoinColumn(name = eventId))
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

