package database.utils;

import org.hibernate.search.exception.EmptyQueryException;

import static database.utils.QueryLiterals.*;

public class EventQuery {
    private String name;
    private String place;
    private String time;
    private String description;
    private String category;

    public void setName(String name) {
        if (name.length() == 0) return;
        this.name = nameQuery + name + percent;
    }

    public void setPlace(String place) {
        if (place.length() == 0) return;
        this.place = placeQuery + place + percent;
    }

    //TODO: Time parser
    public void setTime(String time) {
    }

    public void setDescription(String description) {
        if (description.length() == 0) return;
        String[] words = description.split("[ ]");
        StringBuilder builder = new StringBuilder();
        builder.append(descriptionQuery);
        for (String word : words) {
            builder.append(word);
            builder.append(following);
        }
        int last = builder.lastIndexOf(following);
        builder.delete(last, last + 3);
        builder.append(bracket);
        this.description = builder.toString();
    }

    public void setCategory(String category) {
        if (category.length() == 0) return;
        try {
            this.category = categoryQuery + Integer.parseInt(category);
        } catch (NumberFormatException e) {
            this.category = null;
        }
    }

    public boolean isEmpty() {
        return name == null && place == null && time == null && description == null && category == null;
    }

    public String execute() {
        StringBuilder builder = new StringBuilder();

        if (isEmpty()) {
            throw new EmptyQueryException();
        }

        builder.append(executedQuery);
        if (name != null) {
            builder.append(name);
            builder.append(and);
        }
        if (place != null) {
            builder.append(place);
            builder.append(and);
        }
        if (time != null){
            builder.append(time);
            builder.append(and);
        }
        if (description != null) {
            builder.append(description);
            builder.append(and);
        }
        if (category != null) {
            builder.append(category);
            builder.append(and);
        }
        int last = builder.lastIndexOf(and);
        builder.delete(last, last + 4);

        builder.append(orderQuery);

        return builder.toString();
    }
}
