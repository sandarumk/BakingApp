package com.example.sandarumk.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by dinu on 2/21/18.
 */

public class Recipe implements Parcelable {
    private String recipeID;
    private String recipeName;
    private String recipeImageURL;
    private String numberOfServings;
    private ArrayList<RecipeIngredient> recipeIngredients;
    private ArrayList<RecipeStep> recipeSteps;

    public Recipe(String recipeID, String recipeName, String recipeImageURL, String numberOfServings, ArrayList<RecipeIngredient> recipeIngredients, ArrayList<RecipeStep> recipeSteps) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.recipeImageURL = recipeImageURL;
        this.numberOfServings = numberOfServings;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    protected Recipe(Parcel in) {
        recipeID = in.readString();
        recipeName = in.readString();
        recipeImageURL = in.readString();
        numberOfServings = in.readString();
        recipeIngredients = in.createTypedArrayList(RecipeIngredient.CREATOR);
        recipeSteps = in.createTypedArrayList(RecipeStep.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeID);
        dest.writeString(recipeName);
        dest.writeString(recipeImageURL);
        dest.writeString(numberOfServings);
        dest.writeTypedList(recipeIngredients);
        dest.writeTypedList(recipeSteps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeID() {
        return recipeID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeImageURL() {
        return recipeImageURL;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

}
