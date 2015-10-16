package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by ranjeevmahtani on 10/14/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            Intent appLaunchIntent = new Intent(context, MainActivity.class);
            PendingIntent appLaunchPendingIntent = PendingIntent.getActivity(context,0,appLaunchIntent,0);
            views.setOnClickPendingIntent(R.id.widget_parent_layout, appLaunchPendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context,views);
            } else {
                setRemoteAdapterV11(context, views);
            }

            views.setEmptyView(R.id.widget_today_list, R.id.widget_empty_view);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        if (intent.getAction().equals(FootBallScoresSyncAdapter.ACTION_DATA_UPDATED)) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_today_list);
//        }
//    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_today_list,
                new Intent(context, TodayWidgetRemoteViewsService.class));
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_today_list,
                new Intent(context, TodayWidgetRemoteViewsService.class));
    }
}
