package cz.johnyapps.adastraone_task.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Activity {
    private String activity;
    private Float accessibility;
    private String type;
    private Integer participants;
    private Float price;
    private String link;
    private boolean liked = false;

    @NonNull
    @PrimaryKey
    private String key;

    @Ignore
    private boolean online = false;

    public Activity(String activity,
                    float accessibility,
                    String type,
                    int participants,
                    float price,
                    String link,
                    @NonNull String key) {
        this.activity = activity;
        this.accessibility = accessibility;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.link = link;
        this.key = key;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Float getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(Float accessibility) {
        this.accessibility = accessibility;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
