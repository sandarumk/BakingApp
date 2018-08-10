package com.example.sandarumk.bakingapp.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {

    public static String WIDGET_INGREDIENTS = "INGREDIENTS";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
