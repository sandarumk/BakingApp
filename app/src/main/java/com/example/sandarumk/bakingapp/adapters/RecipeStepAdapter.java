package com.example.sandarumk.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinu on 3/9/18.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.ViewHolder> {

    private List<RecipeStep> mRecipeSteps;
    private OnRecipeStepSelectListener mRecipeStepOnClickListener;

    public RecipeStepAdapter(List<RecipeStep> mRecipeSteps, OnRecipeStepSelectListener mRecipeStepOnClickListener) {
        this.mRecipeSteps = mRecipeSteps;
        this.mRecipeStepOnClickListener = mRecipeStepOnClickListener;
    }

    @Override
    public RecipeStepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipestep_list_content, parent, false);
        return new RecipeStepAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepAdapter.ViewHolder holder, int position) {
        RecipeStep recipeStep = mRecipeSteps.get(position);
        holder.tvNo.setText(String.valueOf(position + 1));
        holder.tvTitle.setText(recipeStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mRecipeSteps != null) {
            return mRecipeSteps.size();
        }
        return 0;
    }

    public void setRecipeSetData(ArrayList<RecipeStep> recipeSteps) {
        mRecipeSteps = recipeSteps;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_no)
        TextView tvNo;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            RecipeStep recipeStep = mRecipeSteps.get(adapterPosition);
            if (mRecipeStepOnClickListener != null) {
                mRecipeStepOnClickListener.onRecipeStepSelect(recipeStep);
            }
        }
    }

    public interface OnRecipeStepSelectListener {
        void onRecipeStepSelect(RecipeStep step);
    }
}
