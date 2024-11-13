package ro.pub.cs.systems.eim.practicaltest01;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class PracticalTest01Service extends Service {

    final static String CHANNEL_ID = "PracticalTest01Service";

    ProcessThread processThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "PracticalTest01Service", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("PracticalTest01Service")
                .setContentText("Service is running")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();

        startForeground(1, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        processThread = new ProcessThread(this, Integer.parseInt(intent.getStringExtra("input1")), Integer.parseInt(intent.getStringExtra("input2")));
        processThread.start();

        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        processThread.stopThread();
    }
}
