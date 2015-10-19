package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import barqsoft.footballscores.service.MyFetchService;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 401;
    public static final int PREMIER_LEGAUE = 398;
    public static final int SEGUNDA_DIVISION = 400;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 399;
    public static final int BUNDESLIGA = 394;

    public static String getLeague(int league_num)
    {
        switch (league_num)
        {
            case SERIE_A : return "Seria A";
            case PREMIER_LEGAUE : return "Premier League";
            case CHAMPIONS_LEAGUE : return "UEFA Champions League";
            case PRIMERA_DIVISION : return "Primera Division";
            case BUNDESLIGA : return "Bundesliga";
            case SEGUNDA_DIVISION : return "Segunda Division";
            default: return "Not known League Please report";
        }
    }
    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            if (match_day <= 6)
            {
                return "Group Stages, Matchday : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return "First Knockout round";
            }
            else if(match_day == 9 || match_day == 10)
            {
                return "QuarterFinal";
            }
            else if(match_day == 11 || match_day == 12)
            {
                return "SemiFinal";
            }
            else
            {
                return "Final";
            }
        }
        else
        {
            return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        if (teamname.startsWith("SpVgg")) return R.drawable.spvgg_furth;
        if (teamname.endsWith("rdoba CF")) return R.drawable.cordoba_cf;
        if (teamname.startsWith("AD Alc")) return R.drawable.alcorcon;
        if (teamname.endsWith("nchengladbach")) return R.drawable.monchenglad;
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go. 
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;

            // Segunda Division
            case "CD Lugo" : return R.drawable.cd_lugo;
            case "Real Valladolid" : return R.drawable.real_valladolid;
            case "Real Oviedo" : return R.drawable.real_oviedo;
            case "RCD Mallorca" : return R.drawable.rcd_mallorca;
            case "Albacete" : return R.drawable.albacete;
            case "AD Alcorcon" : return R.drawable.alcorcon;
            case "Girona FC" : return R.drawable.girona;
            case "Huesca" : return R.drawable.huesca;
            case "UD Almeria" : return R.drawable.almeria;
            case "UE Llagostera" : return R.drawable.llagostera;
            case "Real Zaragoza" : return R.drawable.zaragoza;
            case "Cordoba CF" : return R.drawable.cordoba_cf;

            // BUNDESLIGA2
            case "FC St. Pauli" : return R.drawable.fc_st_pauli;
            case "SpVgg Greuther Furth" : return R.drawable.spvgg_furth;
            case "1. FSV Mainz 05" : return R.drawable.fsv_mainz_05;
            case "Borussia Dortmund" : return R.drawable.borussia_dortmund;

            // BUNDESLIGA
            case "Eintracht Frankfurt" : return R.drawable.eintracht;
            case "Bor. Monchengladbach" : return R.drawable.monchenglad;

            default: return R.drawable.no_icon;
        }
    }

    public static Cursor getLatestScoresCursor (Context context) {
        Intent service_start = new Intent(context, MyFetchService.class);
        context.startService(service_start);

        Cursor latest = context.getContentResolver().query(DatabaseContract.scores_table
                .buildScores(), null, null, null, DatabaseContract.scores_table.DATE_COL + " DESC" +
                ", " + DatabaseContract.scores_table.TIME_COL + " DESC");
        return latest;
    }
}
