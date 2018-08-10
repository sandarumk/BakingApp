package com.example.sandarumk.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.sandarumk.bakingapp.model.Recipe;
import com.example.sandarumk.bakingapp.utils.NetworkUtils;
import com.example.sandarumk.bakingapp.utils.RecipesJSONUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class RecipeListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = RecipeListViewRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private List<Recipe> recipes;
    private int widgetId;


    public RecipeListViewRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        }
    }

    public void onCreate() {
        recipes = new ArrayList<Recipe>();
    }

    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        Recipe recipe = recipes.get(position);
        rv.setTextViewText(android.R.id.text1, recipe.getRecipeName());

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(RecipeAppWidgetProvider.WIDGET_RECIPE_SELECT_ACTION);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeAppWidgetProvider.WIDGET_SELECTION, recipe);
        bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        fillInIntent.putExtras(bundle);
        rv.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return rv;
    }

    public int getCount() {
        return recipes.size();
    }

    public void onDataSetChanged() {
        try {
            String recipeResponse = NetworkUtils
                    .getResponseFromHttpUrl(NetworkUtils.buildUrl(mContext));
            recipes = RecipesJSONUtils.getRecipes(recipeResponse);
        } catch (IOException e) {
            Log.e(TAG, "Error retrieving recipes", e);
        }
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public void onDestroy() {
        recipes.clear();
    }

    public boolean hasStableIds() {
        return true;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

}