package ch.band.inf2019.uk335.notifs;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.model.BaseApplication;

public class SubscriptionNotificationManager implements Observer<List<Subscription>> {

    private NotificationManagerCompat notificationManager;
    private Context context;

    public SubscriptionNotificationManager(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
    }
    private Notification createNotification(String title){
        return new NotificationCompat.Builder(context, BaseApplication.NOTIFICATION_CHANNNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_sheffy)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    @Override
    public void onChanged(List<Subscription> subscriptions) {
        for (int i = 0; i < subscriptions.size(); i++) {
            Notification notification = createNotification(subscriptions.get(i).title);
            Intent notificationIntent = new Intent(context, NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, i);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            long futureInMillis = subscriptions.get(i).dayofnextPayment;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
    }

}
