package cz.johnyapps.adastraone_task.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Activity {
    @NonNull
    private String activity;
    @NonNull
    private Float accessibility;
    @NonNull
    private String type;
    @NonNull
    private Integer participants;
    @NonNull
    private Float price;
    @NonNull
    private String link;
    private boolean liked = false;

    @NonNull
    @PrimaryKey
    private String key;

    public Activity(@NonNull String activity,
                    float accessibility,
                    @NonNull String type,
                    int participants,
                    float price,
                    @NonNull String link,
                    @NonNull String key) {
        this.activity = activity;
        this.accessibility = accessibility;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.link = link;
        this.key = key;
    }

    @NonNull
    public String getActivity() {
        return activity;
    }

    public void setActivity(@NonNull String activity) {
        this.activity = activity;
    }

    @NonNull
    public Float getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(@NonNull Float accessibility) {
        this.accessibility = accessibility;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(@NonNull Integer participants) {
        this.participants = participants;
    }

    @NonNull
    public Float getPrice() {
        return price;
    }

    public void setPrice(@NonNull Float price) {
        this.price = price;
    }

    @NonNull
    public String getLink() {
        return link;
    }

    public void setLink(@NonNull String link) {
        this.link = link;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity1 = (Activity) o;

        if (liked != activity1.liked) return false;
        if (!activity.equals(activity1.activity)) return false;
        if (!accessibility.equals(activity1.accessibility)) return false;
        if (!type.equals(activity1.type)) return false;
        if (!participants.equals(activity1.participants)) return false;
        if (!price.equals(activity1.price)) return false;
        if (!link.equals(activity1.link)) return false;
        return key.equals(activity1.key);
    }

    @Override
    public int hashCode() {
        int result = activity.hashCode();
        result = 31 * result + accessibility.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + participants.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + (liked ? 1 : 0);
        result = 31 * result + key.hashCode();
        return result;
    }
}
