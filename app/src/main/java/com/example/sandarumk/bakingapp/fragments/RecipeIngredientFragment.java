package com.example.sandarumk.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.adapters.RecipeIngredientAdapter;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientFragment extends Fragment {

    public static final String ARG_INGREDIENTS = "Ingredients";

    @BindView(R.id.recipe_ingredient_list)
    RecyclerView recyclerView;
    private List<RecipeIngredient> ingredients;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS);
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        unbinder = ButterKnife.bind(this, view);
        updateList();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void updateList() {
        recyclerView.setAdapter(new RecipeIngredientAdapter(ingredients));
    }
}
