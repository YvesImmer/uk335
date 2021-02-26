package ch.band.inf2019.uk335.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.model.SubscriptionRepository;
import ch.band.inf2019.uk335.notifs.SubscriptionNotificationManager;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Subscription>> subscriptions;
    private LiveData<List<Categorie>> categories;
    private SubscriptionNotificationManager notificationManager;
    SubscriptionRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = SubscriptionRepository.getInstance(application);
        subscriptions = repository.getAllSubscriptions();
        categories = repository.getAllCategories();
        notificationManager = new SubscriptionNotificationManager(application);
        subscriptions.observeForever(notificationManager);
    }
    //region Database
    //region General CRUD
    public LiveData<List<Subscription>> getSubscriptions(){
        return subscriptions;
    }
    public LiveData<List<Categorie>> getCategories() {
        return categories;
    }

    public void insert(Subscription subscription){
        repository.insert(subscription);
    }
    public void insert(Categorie category){
        repository.insert(category);
    }

    public void update(Subscription subscription){
        repository.update(subscription);
    }

    public void update(Categorie categorie){
        repository.update(categorie);
    }

    public void delete(Subscription subscription){
        repository.delete(subscription);
    }

    public void delete(Categorie categorie){
        repository.delete(categorie);
    }
    //endregion
    //region Get Special Categories
    public int getFirstCategoryID(){
        return categories.getValue().get(0).id;
    }

    public Categorie getLastCategory(){
        return categories.getValue().get(categories.getValue().size()-1);
    }

    public Categorie getCategorieById(int ID){
        for (Categorie c:
                categories.getValue()
             ) {
            if(c.id == ID) return c;
        }
        //TODO implement propper exception
        return new Categorie("Error");
    }
    //endregion
    //region Get Special Subscriptions
    public Subscription getSubscriptionById(int ID) {

        for (Subscription s:
                Objects.requireNonNull(subscriptions.getValue())
        ) {
            if (s.subsciriptionid == ID) return s;

        }
        //TODO implement exception
        return null;
    }
    //endregion
    //endregion
    //region Subscriptions
    public void updateAllDayOfNextPayment(){
        if(subscriptions.getValue() != null) {
            for (Subscription s : subscriptions.getValue()
            ) {
                update(s.updateDayOfNextPayment(new Date().getTime()));
            }
        }
    }
    //endregion
}
