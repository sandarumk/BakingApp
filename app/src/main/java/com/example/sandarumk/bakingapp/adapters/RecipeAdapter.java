package com.example.sandarumk.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinu on 9/17/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> mRecipeList;

    private final RecipeAdapterOnClickHandler mRecipeAdapterOnClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mRecipeAdapterOnClickHandler = clickHandler;
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        Context context = holder.mCardView.getContext();
        holder.mTextView.setText(recipe.getRecipeName());
        String recipeImageURL = recipe.getRecipeImageURL();
        if (recipeImageURL != null && !recipeImageURL.isEmpty()) {
            Picasso.get().load(recipeImageURL).into(holder.mImageView);
        } else {
            holder.mImageView.setImageResource(R.drawable.no_recipe);
        }
    }


    @Override
    public int getItemCount() {
        if (mRecipeList == null) {
            return 0;
        }
        return mRecipeList.size();
    }

    public void setRecipeData(List<Recipe> recipes) {
        mRecipeList = recipes;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipeData() {
        return mRecipeList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.tv_list_item)
        TextView mTextView;
        @BindView(R.id.iv_list_background)
        ImageView mImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipeList.get(adapterPosition);
            mRecipeAdapterOnClickHandler.onClick(recipe);
        }
    }
}
