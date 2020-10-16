package database.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int organizer;
    private String name;
    private String place;
    private LocalDateTime time;
    @Column(name = "category")
    private int category_id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "organizer", insertable = false, updatable = false)
    private User orgUser;

    @ManyToMany(mappedBy = "subEvents")
    private Set<User> subscribers = new HashSet<>();

    public Event() {
    }

    public Event(int organizer, String name, String place, LocalDateTime time, int category_id, String description) {
        this.organizer = organizer;
        this.name = name;
        this.place = place;
        this.time = time;
        this.category_id = category_id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getOrganizer() {
        return organizer;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public User getOrgUser() {
        return orgUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
