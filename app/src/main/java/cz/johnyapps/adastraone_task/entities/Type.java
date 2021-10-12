package cz.johnyapps.adastraone_task.entities;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cz.johnyapps.adastraone_task.R;

public enum Type {
    UNKNOWN(R.drawable.type_unknown, true),
    EDUCATION(R.drawable.type_education),
    RECREATIONAL(R.drawable.type_recreational),
    SOCIAL(R.drawable.type_social),
    DIY(R.drawable.type_diy),
    CHARITY(R.drawable.type_charity),
    COOKING(R.drawable.type_cooking),
    RELAXATION(R.drawable.type_relaxation),
    MUSIC(R.drawable.type_music),
    BUSYWORK(R.drawable.type_busywork),
    CONNECT_TO_INTERNET(R.drawable.type_connect_to_internet, true);

    @DrawableRes
    private final int drawableId;
    private final boolean custom;

    Type(@DrawableRes int drawableId) {
        this.drawableId = drawableId;
        this.custom = false;
    }

    Type(@DrawableRes int drawableId, boolean custom) {
        this.drawableId = drawableId;
        this.custom = custom;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public boolean isCustom() {
        return custom;
    }

    @NonNull
    public static Type fromString(@Nullable String value) {
        if (value == null) {
            return getDefault();
        }

        try {
            return Type.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ignored) {

        }

        return getDefault();
    }

    @NonNull
    public static Type getDefault() {
        return UNKNOWN;
    }
}
