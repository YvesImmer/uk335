package ch.band.inf2019.uk335.model;

import android.app.Application;

public class BaseApplication extends Application {
    public static final String NOTIFICATION_CHANNNEL_ID = "Noti";

    private static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
