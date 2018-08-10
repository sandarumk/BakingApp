package com.example.sandarumk.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dinu on 3/7/18.
 */

public class RecipeIngredient implements Parcelable {
    private String quantity;
    private String measure;
    private String name;

    public RecipeIngredient(String quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(name);
    }

    public RecipeIngredient(Parcel parcel) {
        quantity = parcel.readString();
        measure = parcel.readString();
        name = parcel.readString();
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
