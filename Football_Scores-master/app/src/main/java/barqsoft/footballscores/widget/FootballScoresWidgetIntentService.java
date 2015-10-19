package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.ScoresAdapter;

/**
 * Created by Kevin on 10/11/2015.
 */
public class FootballScoresWidgetIntentService extends IntentService {
    private final String LOG_TAG = getClass().getSimpleName();

    public FootballScoresWidgetIntentService() {super("FootballScoresWidgetIntentService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(LOG_TAG, "onHandleIntent");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                FootballScoresWidget.class));

        Cursor latest = Utilies.getLatestScoresCursor(getApplicationContext());
        if (latest == null) return;
        if (!latest.moveToFirst()) {
            latest.close();
            return;
        }

        // Extract the data from the Cursor
//        int weatherId = data.getInt(INDEX_WEATHER_ID);

        String league = latest.getString(ScoresAdapter.COL_LEAGUE);
        String homeTeam = latest.getString(ScoresAdapter.COL_HOME);
        int homeTeamScore = latest.getInt(ScoresAdapter.COL_HOME_GOALS);
        String awayTeam = latest.getString(ScoresAdapter.COL_AWAY);
        int awayTeamScore = latest.getInt(ScoresAdapter.COL_AWAY_GOALS);
        int gameId = latest.getInt(ScoresAdapter.COL_ID);
        String gameTime = latest.getString(ScoresAdapter.COL_MATCHTIME);
        latest.close();

        Log.v(LOG_TAG, "League: " + league + " " + awayTeam + " (" + awayTeamScore + ") @ " +
                homeTeam + " (" + homeTeamScore + ") Game: " + gameId + " @ " + gameTime);

        // Perform this loop procedure for each widget
        for (int appWidgetId : appWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.football_scores_widget);

//            CharSequence widgetText = getApplicationContext().getString(R.string.appwidget_text);
//
//            views.setTextViewText(R.id.score_textview, widgetText);
//            views.setTextViewText(R.id.data_textview, widgetText);
//            views.setTextViewText(R.id.away_name, widgetText);
//            views.setTextViewText(R.id.home_name, widgetText);

            // Add the data to the RemoteViews
            views.setTextViewText(R.id.score_textview, Utilies.getScores(homeTeamScore, awayTeamScore));
            views.setTextViewText(R.id.data_textview, gameTime);
            views.setTextViewText(R.id.away_name, awayTeam);
            views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(awayTeam));
            views.setTextViewText(R.id.home_name, homeTeam);
            views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(homeTeam));


            // Content Descriptions for RemoteViews were only added in ICS MR1
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                setRemoteContentDescription(views, description);
                views.setContentDescription(R.id.score_textview, "Score: " + awayTeamScore + " to" +
                        " " + homeTeamScore);
                views.setContentDescription(R.id.data_textview, "Game Time: " + gameTime);
            }

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
