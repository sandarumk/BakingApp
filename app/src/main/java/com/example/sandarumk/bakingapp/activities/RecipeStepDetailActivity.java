package com.example.sandarumk.bakingapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.fragments.RecipeStepDetailFragment;
import com.example.sandarumk.bakingapp.model.RecipeStep;

import java.util.List;

/**
 * An activity representing a single RecipeStep detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeStepListActivity}.
 */
public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String ARG_ITEMS = "items";
    public static final String ARG_SELECTED_ITEM_ID = "selectedItemId";

    private List<RecipeStep> steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipestep_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        steps = getIntent().getParcelableArrayListExtra(ARG_ITEMS);
        String selectedItemId = getIntent().getStringExtra(ARG_SELECTED_ITEM_ID);

        if (steps == null) {
            Toast.makeText(this, R.string.steps_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ViewPager viewPager = findViewById(R.id.viewpager);
        RecipeStepDetailFragmentAdapter recipeStepDetailFragmentAdapter = new RecipeStepDetailFragmentAdapter(getSupportFragmentManager(), steps);
        viewPager.setAdapter(recipeStepDetailFragmentAdapter);

        if (selectedItemId != null) {
            for (int i = 0; i < steps.size(); i++) {
                if (selectedItemId.equals(steps.get(i).getId())) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static class RecipeStepDetailFragmentAdapter extends FragmentPagerAdapter {

        private List<RecipeStep> items;

        public RecipeStepDetailFragmentAdapter(FragmentManager fragmentManager, List<RecipeStep> items) {
            super(fragmentManager);
            this.items = items;
        }

        @Override
        public int getCount() {
            if (items == null) {
                return 0;
            }
            return items.size();
        }

        @Override
        public Fragment getItem(int position) {
            return RecipeStepDetailFragment.newInstance(items.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).getShortDescription();
        }

    }

}
