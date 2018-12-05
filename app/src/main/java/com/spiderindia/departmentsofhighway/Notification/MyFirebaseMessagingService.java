package com.spiderindia.departmentsofhighway.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.spiderindia.departmentsofhighway.DashBoardActivity;
import com.spiderindia.departmentsofhighway.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by pyr on 28-Aug-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {

        Map<String,String> data = remoteMessage.getData();

        System.out.println("Message Notification array " + data.toString()+" .. "+remoteMessage.getData().get("message"));

        Log.w("fcm", "received notification");


        String titl="";
        try
        {
            JSONObject data1 = new JSONObject(data);

            System.out.println("json "+data1);


            titl = data1.getString("title");
            String message = data1.getString("message");
            boolean isBackground = data1.getBoolean("is_background");
            String imageUrl = data1.getString("image");
            String timestamp = data1.getString("timestamp");

            Intent resultIntent = new Intent(getApplicationContext(), DashBoardActivity.class);
            resultIntent.putExtra("message", message);

            showNotificationMessage(getApplicationContext(), titl, message, timestamp, resultIntent);



        } catch (JSONException e) {
            Log.e("", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("", "Exception: " + e.getMessage());
        }



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            System.out.println("Message data payload: " + remoteMessage.getData());
        }
        System.out.println("AlbumFoundOrNot Nott out");

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        System.out.println("fcm recived a msg1242354");


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(messageBody)
                .setAutoCancel(false)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        System.out.println("Message Notification Body "+messageBody);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        System.out.println("showNotificationMessage ");
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
