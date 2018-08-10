package com.example.sandarumk.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.sandarumk.bakingapp.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

class StepListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = StepListViewRemoteViewsFactory.class.getSimpleName();

    private Context mContext;
    private List<RecipeIngredient> ingredients;


    public StepListViewRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        ingredients = intent.getParcelableArrayListExtra(RecipeWidgetService.WIDGET_INGREDIENTS);
        if (ingredients == null && intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            ingredients = RecipeAppWidgetProvider.INGREDIENT_DATE.get(intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1));
        }
    }

    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), android.R.layout.simple_list_item_1);
        RecipeIngredient ingredient = ingredients.get(position);
        rv.setTextViewText(android.R.id.text1, ingredient.getName() + "  " + ingredient.getQuantity() + " " + ingredient.getMeasure());
        return rv;
    }

    public int getCount() {
        return ingredients.size();
    }

    @Override
    public void onCreate() {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
    }

    public void onDataSetChanged() {

    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public void onDestroy() {
        ingredients.clear();
    }

    public boolean hasStableIds() {
        return true;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

}