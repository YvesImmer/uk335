package ch.band.inf2019.uk335.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Categorie.class, Subscription.class},version = 1)
public abstract class SubscriptionDatabase extends RoomDatabase {
    private static SubscriptionDatabase instance;

    public abstract CategorieDao categorieDao();

    public abstract SubscriptionDao subscriptionDao();

    public static synchronized SubscriptionDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SubscriptionDatabase.class,
                    "subscribtion_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                CategorieDao categorieDao = SubscriptionDatabase.instance.categorieDao();
                categorieDao.insert(
                        new Categorie("Streaming"),
                        new Categorie("Mobilität")

                );
            });
        }
    };
}
