package com.example.sandarumk.bakingapp.utils;

import android.util.Log;

import com.example.sandarumk.bakingapp.model.Recipe;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;
import com.example.sandarumk.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinu on 2/21/18.
 */

public class RecipesJSONUtils {


    private static final String TAG = RecipesJSONUtils.class.getSimpleName();

    private static final String JSON_ID = "id";
    private static final String JSON_NAME = "name";
    private static final String JSON_INGREDIENTS = "ingredients";
    private static final String JSON_QUANTITY = "quantity";
    private static final String JSON_MEASURE = "measure";
    private static final String JSON_INGREDIENT = "ingredient";
    private static final String JSON_STEPS = "steps";
    private static final String JSON_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_VIDEO_URL = "videoURL";
    private static final String JSON_THUMBNAIL_URL = "thumbnailURL";
    private static final String JSON_SERVINGS = "servings";
    private static final String JSON_IMAGE = "image";


    public static List<Recipe> getRecipes(String recipeListJSONString) {
        List<Recipe> recipes = new ArrayList<>();
        Log.d(TAG, recipeListJSONString);
        JSONArray recipesJSON;
        try {
            recipesJSON = new JSONArray(recipeListJSONString);

            for (int i = 0; i < recipesJSON.length(); i++) {

                JSONObject recipe = recipesJSON.getJSONObject(i);
                String recipeID = recipe.getString(JSON_ID);
                String name = recipe.getString(JSON_NAME);

                JSONArray ingredientsArray = recipe.getJSONArray(JSON_INGREDIENTS);
                ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>(ingredientsArray.length());

                for (int j = 0; j < ingredientsArray.length(); j++) {
                    JSONObject ingredients = ingredientsArray.getJSONObject(j);
                    String quantity = ingredients.getString(JSON_QUANTITY);
                    String measure = ingredients.getString(JSON_MEASURE);
                    String ingredient = ingredients.getString(JSON_INGREDIENT);
                    RecipeIngredient recipeIngredient = new RecipeIngredient(quantity, measure, ingredient);
                    recipeIngredients.add(recipeIngredient);
                }

                JSONArray stepsArray = recipe.getJSONArray(JSON_STEPS);
                ArrayList<RecipeStep> recipeSteps = new ArrayList<>(stepsArray.length());

                for (int k = 0; k < stepsArray.length(); k++) {
                    JSONObject steps = stepsArray.getJSONObject(k);
                    String stepId = steps.getString(JSON_ID);
                    String shortDescription = steps.getString(JSON_SHORT_DESCRIPTION);
                    String description = steps.getString(JSON_DESCRIPTION);
                    String videoURL = steps.getString(JSON_VIDEO_URL);
                    String thumbnailURL = steps.getString(JSON_THUMBNAIL_URL);
                    RecipeStep recipeStep = new RecipeStep(stepId, shortDescription, description, videoURL, thumbnailURL);
                    recipeSteps.add(recipeStep);
                }

                String servings = recipe.getString(JSON_SERVINGS);
                String imageURL = recipe.getString(JSON_IMAGE);
                Recipe recipeObject = new Recipe(recipeID, name, imageURL, servings, recipeIngredients, recipeSteps);
                recipes.add(recipeObject);
                Log.d(TAG, "Recipe " + recipeID + " : " + name);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON", e);
        }

        return recipes;

    }

//        public static List<Trailer> getTrailers(String trailersURL) {
//            List<Trailer> trailers = new ArrayList<>();
//            Log.d(TAG, trailersURL);
//            JSONObject trailersJSON;
//            try {
//                trailersJSON = new JSONObject(trailersURL);
//
//                JSONArray resultsArray = trailersJSON.getJSONArray(JSON_RESULTS);
//
//                for (int i = 0; i < resultsArray.length(); i++) {
//
//                    JSONObject trailer = resultsArray.getJSONObject(i);
//                    String key = trailer.getString(JSON_KEY);
//                    String name = trailer.getString(JSON_NAME);
//                    String id = trailer.getString(JSON_ID);
//                    Trailer trailerObject = new Trailer(key,name,id);
//                    Log.d(TAG, "Movie Trailer " + name + "Added");
//                    trailers.add(trailerObject);
//
//                }
//            } catch (JSONException e) {
//                Log.e(TAG, "Unable to parse JSON", e);
//            }
//            return trailers;
//        }

//        public static List<Review> getReviews(String reviewsURL) {
//            List<Review> reviews = new ArrayList<>();
//            Log.d(TAG, reviewsURL);
//            JSONObject reviewsJSON;
//            try {
//                reviewsJSON = new JSONObject(reviewsURL);
//
//                JSONArray resultsArray = reviewsJSON.getJSONArray(JSON_RESULTS);
//
//                for (int i = 0; i < resultsArray.length(); i++) {
//
//                    JSONObject review = resultsArray.getJSONObject(i);
//                    String author = review.getString(JSON_AUTHOR);
//                    String content = review.getString(JSON_CONTENT);
//                    String id = review.getString(JSON_ID);
//                    Review reviewObject = new Review(id,content,author);
//                    Log.d(TAG, "Movie Review By " + author + "Added");
//                    reviews.add(reviewObject);
//
//                }
//            } catch (JSONException e) {
//                Log.e(TAG, "Unable to parse JSON", e);
//            }
//            return reviews;
//        }
}
