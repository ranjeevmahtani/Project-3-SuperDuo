package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by ranjeevmahtani on 10/14/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TodayWidgetRemoteViewsService extends RemoteViewsService {

    public final String LOG_TAG = TodayWidgetRemoteViewsService.class.getSimpleName();

    public static final int COL_HOME = 3;
    public static final int COL_AWAY = 4;
    public static final int COL_HOME_GOALS = 6;
    public static final int COL_AWAY_GOALS = 7;
    public static final int COL_DATE = 1;
    public static final int COL_LEAGUE = 5;
    public static final int COL_MATCHDAY = 9;
    public static final int COL_ID = 8;
    public static final int COL_MATCHTIME = 2;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        final String LOG_TAG = RemoteViewsFactory.class.getSimpleName();

        return new RemoteViewsFactory() {

            private Cursor data = null;

            @Override
            public void onCreate() {
                updateScores();
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                updateScores();
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION
                        || data == null
                        || !data.moveToPosition(position)
                        ) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_list_item);

                String homeName = data.getString(COL_HOME);
                String awayName = data.getString(COL_AWAY);
                int homeGoals = data.getInt(COL_HOME_GOALS);
                int awayGoals = data.getInt(COL_AWAY_GOALS);
                String matchTime = data.getString(COL_MATCHTIME);
                String date = data.getString(COL_DATE);

                views.setTextViewText(R.id.home_name, homeName);
                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                        data.getString(COL_HOME)));
                views.setTextViewText(R.id.away_name, awayName);
                views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                        data.getString(COL_AWAY)));
                views.setTextViewText(R.id.widget_date_textview, date);
                views.setTextViewText(R.id.score_textview, Utilies.getScores(homeGoals, awayGoals));
                views.setTextViewText(R.id.data_textview, matchTime);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getInt(COL_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            private void updateScores() {
                final long identityToken = Binder.clearCallingIdentity();

                data = getContentResolver().query(DatabaseContract.BASE_CONTENT_URI,
                        null,
                        null,
                        null,
                        DatabaseContract.scores_table.DATE_COL + " ASC");

//                data.moveToFirst();
//                String matchtime = data.getString(COL_MATCHTIME);
//                Log.d(LOG_TAG, String.valueOf("1st Matchtime = " + matchtime));

                    Binder.restoreCallingIdentity(identityToken);
            }
        };
    }

}
