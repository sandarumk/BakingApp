package com.example.sandarumk.bakingapp.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeStepWidgetService extends RemoteViewsService {

    public static String WIDGET_STEPS = "STEPS";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StepListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
