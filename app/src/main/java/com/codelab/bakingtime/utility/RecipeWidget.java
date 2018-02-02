package com.codelab.bakingtime.utility;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.codelab.bakingtime.R;
import com.codelab.bakingtime.activity.FavRecipeActivity;
import com.codelab.bakingtime.activity.MainActivity;
import com.codelab.bakingtime.data.preference.AppPreference;
import com.codelab.bakingtime.data.preference.PrefKey;

public class RecipeWidget extends AppWidgetProvider {

    private static Context mContext;
    private static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        mContext = context;
        views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, FavRecipeActivity.class), 0);
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

        setValues();

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static private void setValues() {
        String title = AppPreference.getInstance(mContext).getString(PrefKey.TITLE);
        String description = AppPreference.getInstance(mContext).getString(PrefKey.DESCRIPTION);

        if(title != null) {
            views.setTextViewText(R.id.title, title);
        } else {
            views.setTextViewText(R.id.title, "");
        }

        if(description != null) {
            views.setTextViewText(R.id.description, description);
        } else {
            views.setTextViewText(R.id.description, mContext.getResources().getString(R.string.no_widget_content));
        }
    }
}

