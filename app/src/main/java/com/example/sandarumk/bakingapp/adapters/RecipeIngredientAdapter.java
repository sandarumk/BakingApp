package com.example.sandarumk.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinu on 3/9/18.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder> {

    private List<RecipeIngredient> mRecipeIngredients;

    public RecipeIngredientAdapter(List<RecipeIngredient> mRecipeIngredients) {
        this.mRecipeIngredients = mRecipeIngredients;
    }

    @Override
    public RecipeIngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredient_list_content, parent, false);
        return new RecipeIngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientAdapter.ViewHolder holder, int position) {
        RecipeIngredient ingredient = mRecipeIngredients.get(position);
        holder.tvName.setText(ingredient.getName());
        holder.tvQuantity.setText(ingredient.getQuantity());
        holder.tvMeasure.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mRecipeIngredients != null) {
            return mRecipeIngredients.size();
        }
        return 0;
    }

    public void setRecipeSetData(ArrayList<RecipeIngredient> recipeIngredients) {
        mRecipeIngredients = recipeIngredients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_measure)
        TextView tvMeasure;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
