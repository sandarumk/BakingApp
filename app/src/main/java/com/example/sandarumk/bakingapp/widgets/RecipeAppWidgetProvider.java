package com.example.sandarumk.bakingapp.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.widget.RemoteViews;

import com.example.sandarumk.bakingapp.R;
import com.example.sandarumk.bakingapp.model.Recipe;
import com.example.sandarumk.bakingapp.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeAppWidgetProvider extends AppWidgetProvider {
    public static String WIDGET_RECIPE_SELECT_ACTION = "WIDGET_RECIPE_SELECT_ACTION";
    public static String WIDGET_SELECTION = "SELECTION";

    public static final Map<Integer, List<RecipeIngredient>> INGREDIENT_DATE = new HashMap<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context, RecipeAppWidgetProvider.class);
        for (int i = 0; i < appWidgetIds.length; ++i) {

            List<RecipeIngredient> recipeIngredients = INGREDIENT_DATE.get(appWidgetIds[i]);
            Intent intent;
            if (recipeIngredients != null) {
                intent = new Intent(context, RecipeStepWidgetService.class);
                intent.putParcelableArrayListExtra(RecipeWidgetService.WIDGET_INGREDIENTS, new ArrayList<Parcelable>(recipeIngredients));
            } else {
                intent = new Intent(context, RecipeWidgetService.class);
            }
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_list);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);

            // Adding collection list item handler
            final Intent onItemClick = new Intent(context, RecipeAppWidgetProvider.class);
            onItemClick.setAction(WIDGET_RECIPE_SELECT_ACTION);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view,
                    onClickPendingIntent);


            rv.setEmptyView(R.id.list_view, R.id.empty_view);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(WIDGET_RECIPE_SELECT_ACTION) && intent.getExtras() != null) {
            Recipe recipe = intent.getExtras().getParcelable(WIDGET_SELECTION);
            if (recipe != null) {
//                Toast.makeText(context, "position=" + recipe.getRecipeSteps().size(), Toast.LENGTH_LONG).show();
                Intent newIntent = new Intent(context, RecipeAppWidgetProvider.class);
                newIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                Context applicationContext = context.getApplicationContext();
                int[] ids = AppWidgetManager.getInstance(applicationContext)
                        .getAppWidgetIds(new ComponentName(applicationContext, RecipeAppWidgetProvider.class));
//                newIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1));
                newIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

                INGREDIENT_DATE.put(intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1), recipe.getRecipeIngredients());

                applicationContext.sendBroadcast(newIntent);
            }
        }
        super.onReceive(context, intent);
    }
}
