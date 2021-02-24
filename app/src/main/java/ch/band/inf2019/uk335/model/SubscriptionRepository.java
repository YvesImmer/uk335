package ch.band.inf2019.uk335.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;

import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.CategorieDao;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.db.SubscriptionDao;
import ch.band.inf2019.uk335.db.SubscriptionDatabase;

public class SubscriptionRepository {
    private static SubscriptionRepository instance;
    private final Executor executor;
    private CategorieDao categorieDao;
    private SubscriptionDao subscriptionDao;
    private LiveData<List<Categorie>> allCategories;
    private LiveData<List<Subscription>> allSubscriptions;

    private SubscriptionRepository(Application application, Executor executor){
        this.executor = executor;
        SubscriptionDatabase database = SubscriptionDatabase.getInstance(application);
        categorieDao = database.categorieDao();
        subscriptionDao = database.subscriptionDao();
        allCategories = categorieDao.getAllCategories();
        allSubscriptions = subscriptionDao.getAllSubscriptions();

    }

    public static synchronized SubscriptionRepository getInstance(Application application){
        if (instance == null){
            instance = new SubscriptionRepository(application,new NewThreadExecutor());
        }
        return instance;
    }

    /**
     * Inserts Subscriptions in to the Database
     * @param subscriptions Subscriptions to be inserted in to the Database
     */
    public void insert(Subscription... subscriptions){
        executor.execute(() -> subscriptionDao.insert(subscriptions));
    }

    /**
     * Inserts Categories in to the Database
     * @param categories Categories to to be inserted in to the Database
     */
    public void insert(Categorie... categories){
        executor.execute(() -> categorieDao.insert(categories));
    }

    /**
     * Updates Subscriptions in the Database
     * @param subscriptions Subscriptions to be updated in the Database
     */
    public void update(Subscription... subscriptions){
        executor.execute(() -> subscriptionDao.updateSubscriptions(subscriptions));
    }
    /**
     * Updates Subscriptions in the Database
     * @param categories Categories to be updated in the Database
     */
    public void update(Categorie... categories){
        executor.execute(() -> categorieDao.updateCategories(categories));
    }
    /**
     * Deletes Subscriptions in the Database
     * @param subscriptions Subscriptions to be deleted in the Database
     */
    public void delete(Subscription... subscriptions){
        executor.execute(() -> subscriptionDao.deleteSubscriptions(subscriptions));
    }
    /**
     * Deletes Subscriptions in the Database
     * @param categories Categories to be deleted in the Database
     */
    public void delete(Categorie... categories){
        executor.execute(() -> categorieDao.deleteCategories(categories));
    }

    /**
     * Returns all the Categories in the Database
     * @return All Categories
     */
    public LiveData<List<Categorie>> getAllCategories() {
        return allCategories;
    }

    /**
     * Returns all Subscriptions in the Database
     * @return All Subscriptions orderd by the day of the next Paymenent
     */
    public LiveData<List<Subscription>> getAllSubscriptions() {
        return allSubscriptions;
    }

    /*public LiveData<List<Subscription>> getSubscriptionsfromCategorieID(long categorieid){
        final LiveData<List<Subscription>> results;
        executor.execute(new Runnable() {
            @Override
            public void run() {
               subscriptionDao.findSubscriptionsForCategorie(categorieid);

            }
        });

    }*/
}
