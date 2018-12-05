package com.spiderindia.departmentsofhighway.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pyr on 19-May-18.
 */

public class NetUtils {
    static public boolean isNetworkConnect(Context context)
    {
        try
        {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

/*
    static public boolean isLogOut(Context context)
    {
        try
        {

            SharedPreferences spf =context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String userId = spf.getString("userId", "");
            String authenticationToken = spf.getString("authenticationToken", "");
            String response="";

            Config userFunction1 = new Config();
            JSONObject json = userFunction1.LogoutProcess(userId,authenticationToken);


            try {
                response = json.getString("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(!(response.equalsIgnoreCase("")) && (response.equalsIgnoreCase("TRUE")))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
*/

    static public String changeNewDateFormat(String date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy HH:m");

        Calendar c = Calendar.getInstance();
        try
        {
            c.setTime(sdf.parse(date));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        SimpleDateFormat format  = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
        String finalDate = format.format(c.getTime());

        return finalDate;
    }

}
