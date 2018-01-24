package com.codelab.bakingtime.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.codelab.bakingtime.R;

public class Utils {


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void noInternetWarning(TextView view, final Context context) {
        view.setText(context.getResources().getString(R.string.please_connect));
        Snackbar snackbar = Snackbar.make(view, context.getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(context.getString(R.string.connect), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        snackbar.show();
    }

    public static void shareText(Activity activity, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        activity.startActivity(sendIntent);
    }


}
