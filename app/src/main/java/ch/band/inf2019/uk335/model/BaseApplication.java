package ch.band.inf2019.uk335.model;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import ch.band.inf2019.uk335.R;


public class BaseApplication extends Application {
    public static final String NOTIFICATION_CHANNNEL_ID = "subscriptions";

    private static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
        instance = this;
    }
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNNEL_ID,
                    "Subscription Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            notificationChannel.setDescription(getString(R.string.notification_channel_desc));

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

    }
}
