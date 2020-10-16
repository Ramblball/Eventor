package database.model;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private byte[] salt;
    private byte[] hash;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orgUser", orphanRemoval = true)
    private List<Event> ownEvents = new LinkedList<>();


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "users_events",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    Set<Event> subEvents = new HashSet<>();


    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        setPassword(password);
    }

    private void setPassword(String password) {
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
            return hash == md.digest(pass.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getOwnEvents() {
        return ownEvents;
    }

    public void addOwnEvent(Event event) {
        ownEvents.add(event);
    }

    public void removeOwnEvent(Event event) {
        ownEvents.remove(event);
    }

    public Set<Event> getSubEvents() {
        return subEvents;
    }

    public void addSubEvents(Event event) {
        subEvents.add(event);
    }

    public void removeSubEvents(Event event) {
        subEvents.remove(event);
    }
}
