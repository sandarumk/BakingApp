package com.example.sandarumk.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.adapters.RecipeAdapter;
import com.example.sandarumk.bakingapp.model.Recipe;
import com.example.sandarumk.bakingapp.utils.NetworkUtils;
import com.example.sandarumk.bakingapp.utils.RecipesJSONUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BUNDLE_RECYCLER_LAYOUT = "RECYCLER_VIEW_LAYOUT";
    private static final String STATE_KEY_RECIPES = "recipes";

    private RecipeAdapter mRecipeAdapter;
    private Parcelable recyclerState;
    @BindView(R.id.rv_recipe_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        setTitle(R.string.app_name);

        recyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState == null) {
            FetchRecipesTask fetchRecipesTask = new FetchRecipesTask();
            fetchRecipesTask.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        recyclerState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerState);
        outState.putParcelableArrayList(STATE_KEY_RECIPES, new ArrayList<Parcelable>(mRecipeAdapter.getRecipeData()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            recyclerState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);

            updateRecipesView(savedInstanceState.<Recipe>getParcelableArrayList(STATE_KEY_RECIPES));
        }
    }


    @Override
    public void onClick(Recipe recipe) {
        Context context = this;

        Intent intent = new Intent(context, RecipeStepListActivity.class);
        intent.putExtra(RecipeStepListActivity.INTENT_EXTRA_RECIPE, recipe);
        startActivity(intent);

    }

    private void updateRecipesView(List<Recipe> recipes) {
        mRecipeAdapter.setRecipeData(recipes);
        if (recyclerState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerState);
            recyclerState = null;
        }
    }

    public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {


        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            List<Recipe> recipeData = null;
            try {
                String recipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(NetworkUtils.buildUrl(getApplicationContext()));
                recipeData = RecipesJSONUtils.getRecipes(recipeResponse);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return recipeData;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            if (recipes != null) {
                updateRecipesView(recipes);
            }
        }
    }
}

