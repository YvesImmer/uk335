package ch.band.inf2019.uk335.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.model.OnDBOperationCompleteListener;
import ch.band.inf2019.uk335.model.SubscriptionRepository;
import ch.band.inf2019.uk335.notifs.SubscriptionNotificationManager;

public class MainViewModel extends AndroidViewModel{

    private LiveData<List<Subscription>> livesubscriptions;
    private List<Subscription> subscriptions;
    private LiveData<List<Categorie>> livecategories;
    private List<Categorie> categories;
    private SubscriptionNotificationManager notificationManager;
    SubscriptionRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = SubscriptionRepository.getInstance(application);
        livesubscriptions = repository.getAllSubscriptions();
        livecategories = repository.getAllCategories();
        livecategories.observeForever(new CategoriesObserver());
        livesubscriptions.observeForever(new SubscriptionObserver());
        notificationManager = new SubscriptionNotificationManager(application);
        livesubscriptions.observeForever(notificationManager);
    }
    //region Database
    //region General CRUD
    public LiveData<List<Subscription>> getSubscriptions(){
        return livesubscriptions;
    }
    public LiveData<List<Categorie>> getCategories() {
        return livecategories;
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

            return categories.get(0).id;

    }

    public Categorie getLastCategory(){
        int maxid = 0;
        Categorie lastCategorie = null;
        for (Categorie categorie: categories
        ) {
            if(categorie.id> maxid){
                maxid = categorie.id;
                lastCategorie = categorie;
            }
        }
        return  lastCategorie;
    }



    public Categorie getCategorieById(int ID){
        for (Categorie c:
                categories
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
                Objects.requireNonNull(subscriptions)
        ) {
            if (s.subsciriptionid == ID) return s;

        }
        //TODO implement exception
        return null;
    }
    public Subscription getLastSubscription() {
        int maxid = 0;
        Subscription lastSubscription = null;
        for (Subscription s: subscriptions
             ) {
            if(s.subsciriptionid > maxid){
                maxid = s.subsciriptionid;
                lastSubscription = s;
            }
        }
        return  lastSubscription;
    }

    public Categorie getCategorieFromSubscriptionID(int subscriptionId){
        final Categorie[] categorie = new Categorie[1];
        repository.getCategorieFromSubscriptionID(subscriptionId, new OnDBOperationCompleteListener() {
            @Override
            public void onDBOperationComplete(Object object) {
                categorie[0] = (Categorie)object;
            }
        });
        return categorie[0];

    }
    //endregion
    //endregion
    //region Calculations
    public void updateAllDayOfNextPayment(){
        if(livesubscriptions.getValue() != null) {
            for (Subscription s : livesubscriptions.getValue()
            ) {
                update(s.updateDayOfNextPayment(new Date().getTime()));
            }
        }
    }
    public int getCostYear(){
        int cost = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        List<Subscription> subscriptionscopy = new ArrayList<Subscription>(subscriptions);
        calendar.add(Calendar.YEAR,1);
        long yearInFutur = calendar.getTimeInMillis();
        for (Subscription s:subscriptionscopy
             ) {
            if(s.dayofnextPayment<yearInFutur)
            if(s.frequency == 2){
                cost+=s.price;
            }else if (s.frequency == 1){
                do {
                    cost += s.price;
                    s.updateDayOfNextPayment(yearInFutur);
                }while (yearInFutur>s.dayofnextPayment);
            }else{
                cost+= s.price;

            }
        }
        return cost;
    }
    public int getCostMonth(){
        int cost = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        List<Subscription> subscriptionscopy = new ArrayList<Subscription>(subscriptions);
        calendar.add(Calendar.YEAR,1);
        long monthInFutur = calendar.getTimeInMillis();
        for (Subscription s:subscriptionscopy
        ) {
            if(s.dayofnextPayment<monthInFutur)
                if(s.frequency == 2){
                    cost+=s.price;
                }else if (s.frequency == 1){
                        cost += s.price;
                }else{
                    cost+= s.price;

                }
        }
        return cost;
    }

    //endregion
    //regions Observers
    private class SubscriptionObserver implements Observer<List<Subscription>>{


        @Override
        public void onChanged(List<Subscription> data) {
            subscriptions = data;
        }
    }
    private class CategoriesObserver implements Observer<List<Categorie>>{


        @Override
        public void onChanged(List<Categorie> data) {
            categories = data;
        }
    }

    //endregion
}
