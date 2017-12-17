package com.codingdojoangola.server.network;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.codingdojoangola.R;
import com.codingdojoangola.models.chat.Message;
import com.codingdojoangola.ui.chat.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class FirebaseService extends FirebaseMessagingService {

    private String name;
    FirebaseUser fbUtilizador;
    private FirebaseAuth mAuth;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        /**
         * be sure that data size > 0
         */

        if (remoteMessage.getData().size() > 0) {

            String messageContent = remoteMessage.getData().get("message");
            String userId = remoteMessage.getData().get("id");
            String timestamp = remoteMessage.getData().get("date_time");
            name = remoteMessage.getData().get("name");

            // Create new message and assign value to it
            Message message = new Message();
            message.setSender(userId);
            message.setMessage(messageContent);
            message.setDate_time(timestamp);

            mAuth = FirebaseAuth.getInstance();
            fbUtilizador = mAuth.getCurrentUser();

            // check if the sender of message is current user or not
            if (!userId.equals(fbUtilizador.getUid())) {

                // check if app in background or not

                if (isAppIsInBackground(this)) {
                    // app is in background show notification to user
                    sendNotification(message);
                } else {
                    // app is forground and user see it now send broadcast to update chat
                    // you can send broadcast to do anything if you want !
                    Intent intent = new Intent("UpdateChateActivity");
                    intent.putExtra("msg", message);
                    sendBroadcast(intent);

                }
            }


        }

    }

    /**
     * Method check if app is in background or in foreground
     *
     * @param context this contentx
     * @return true if app is in background or false if app in foreground
     */

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    /**
     * Method send notification
     *
     * @param message message object
     */
    private void sendNotification(Message message) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("message", message);
        intent.putExtra("id", message.getSender());
        intent.putExtra("name", name);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Chat cDa")
                .setContentText(message.getMessage())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}

