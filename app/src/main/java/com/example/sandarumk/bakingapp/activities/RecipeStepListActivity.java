package com.example.sandarumk.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.adapters.RecipeStepAdapter;
import com.example.sandarumk.bakingapp.fragments.RecipeIngredientFragment;
import com.example.sandarumk.bakingapp.fragments.RecipeStepDetailFragment;
import com.example.sandarumk.bakingapp.model.Recipe;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;
import com.example.sandarumk.bakingapp.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of RecipeSteps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeStepListActivity extends AppCompatActivity implements RecipeStepAdapter.OnRecipeStepSelectListener {

    public static String TAG = RecipeStepListActivity.class.getSimpleName();
    public static final String INTENT_EXTRA_RECIPE = "Recipe";
    private RecipeStepAdapter mRecipeStepAdapter;
    @BindView(R.id.recipestep_list)
    RecyclerView recyclerView;
    private List<RecipeStep> recipeSteps;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_list);
        ButterKnife.bind(this);
        Intent intentThatStartedThisActivity = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.recipe_step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        Recipe recipe = null;
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(INTENT_EXTRA_RECIPE)) {
            recipe = intentThatStartedThisActivity.getParcelableExtra(INTENT_EXTRA_RECIPE);
            Log.d(TAG, "Recipe Name : " + recipe.getRecipeName());
        }

        if (recipe == null) {
            Toast.makeText(this, R.string.recipe_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        assert recyclerView != null;
        setupRecyclerView(recipe.getRecipeSteps());

        TextView recipeIngredients = findViewById(R.id.tv_ingredient);
        final ArrayList<RecipeIngredient> ingredients = new ArrayList<>(recipe.getRecipeIngredients());
        recipeIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIngredientSelect(ingredients);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(List<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
        mRecipeStepAdapter = new RecipeStepAdapter(recipeSteps, this);
//        mRecipeStepAdapter.setRecipeSetData(recipeSteps);
        recyclerView.setAdapter(mRecipeStepAdapter);
    }

    @Override
    public void onRecipeStepSelect(RecipeStep step) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeStepDetailFragment.ARG_ITEM, step);
            RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
            fragment.setArguments(arguments);
            RecipeStepListActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeStepDetailActivity.class);
            intent.putExtra(RecipeStepDetailActivity.ARG_SELECTED_ITEM_ID, step.getId());
            intent.putParcelableArrayListExtra(RecipeStepDetailActivity.ARG_ITEMS, new ArrayList<Parcelable>(recipeSteps));
            this.startActivity(intent);
        }
    }

    private void onIngredientSelect(ArrayList<RecipeIngredient> ingredients) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(RecipeIngredientFragment.ARG_INGREDIENTS, ingredients);
            RecipeIngredientFragment fragment = new RecipeIngredientFragment();
            fragment.setArguments(arguments);
            RecipeStepListActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeIngredientActivity.class);
            intent.putParcelableArrayListExtra(RecipeIngredientFragment.ARG_INGREDIENTS, ingredients);
            this.startActivity(intent);
        }
    }
}
