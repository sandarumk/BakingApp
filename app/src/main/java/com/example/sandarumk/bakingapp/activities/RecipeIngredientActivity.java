package com.example.sandarumk.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.fragments.RecipeIngredientFragment;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;

import java.util.ArrayList;

public class RecipeIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<RecipeIngredient> ingredients = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RecipeIngredientFragment.ARG_INGREDIENTS)) {
            ingredients = intent.getParcelableArrayListExtra(RecipeIngredientFragment.ARG_INGREDIENTS);
        }

        if (ingredients == null) {
            Toast.makeText(this, R.string.ingredients_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(RecipeIngredientFragment.ARG_INGREDIENTS, ingredients);
        RecipeIngredientFragment fragment = new RecipeIngredientFragment();
        fragment.setArguments(arguments);
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
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

}
