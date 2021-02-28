package ch.band.inf2019.uk335.viewmodel;

import android.app.Application;
import android.graphics.Color;

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
import ch.band.inf2019.uk335.model.SubscriptionRepository;
import ch.band.inf2019.uk335.notifs.SubscriptionNotificationManager;

public class MainViewModel extends AndroidViewModel{

    private LiveData<List<Subscription>> livesubscriptions;
    private List<Subscription> subscriptions;
    private LiveData<List<Categorie>> livecategories;
    private List<Categorie> categories;
    private SubscriptionNotificationManager notificationManager;
    SubscriptionRepository repository;
    private boolean isYearlyMode = false;
    private int cost ;
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
    /**
     * Returns a LiveData List of all Categories in the Database
     * @return LiveData List with all Categories
     */
    public LiveData<List<Categorie>> getCategories() {
        return livecategories;
    }

    /**
     * Insert a Subscription int DB
     * @param subscription Subscription to insert
     */
    public void insert(Subscription subscription){
        repository.insert(subscription);
    }

    /**
     * Inserts a Category in to the DB
     * @param category Category to be inserted
     */
    public void insert(Categorie category){
        repository.insert(category);
    }

    /**
     * Updates the subscription with the same id as the one Passed
     * @param subscription Subscription to update with
     */
    public void update(Subscription subscription){
        repository.update(subscription);
    }
      /** Updates the Category with the same id as the one Passed
     * @param categorie Category to update with
     */
    public void update(Categorie categorie){
        repository.update(categorie);
    }

    /**
     * Deletes Subscription with the same ID as the one passed
     * @param subscription Subscription that should be deleted
     */
    public void delete(Subscription subscription){
        repository.delete(subscription);
    }

    /**
     * Deletes Category with the same ID as the one passed
     * @param categorie Category that should be deleted
     */
    public void delete(Categorie categorie){
        repository.delete(categorie);
    }
    //endregion
    //region Get Special Categories

    /**
     * Returns the Id of the first Category in the DB
     * @return The First ID
     */
    public int getFirstCategoryID(){

            return categories.get(0).id;

    }



    /**
     * Returns the Category with this ID
     * @param ID The ID of the Category that should be returned
     * @return Category with the ID that was passed -if not found returns Category Named Error
     */
    public Categorie getCategorieById(int ID){
        for (Categorie c:
                categories
             ) {
            if(c.id == ID) return c;
        }
        //TODO implement propper exception
        return new Categorie("Error", Color.RED);
    }
    //endregion
    //region Get Special Subscriptions

    /**
     * Returns the Subscription with this ID
     * @param ID The ID of the Subscription that should be returned
     * @return Subscription with the passed Id if not found null
     */
    public Subscription getSubscriptionById(int ID) {

        for (Subscription s:
                subscriptions
        ) {
            if (s.subsciriptionid == ID) return s;

        }
        //TODO implement exception
        return null;
    }

    //endregion
    //endregion
    //region Calculations

    /**
     * Updates all the next Payment Days in the past to ones in the Future from Subscriptions
     */
    public void updateAllDayOfNextPayment(){
        if(livesubscriptions.getValue() != null) {
            for (Subscription s : livesubscriptions.getValue()
            ) {
                update(s.updateDayOfNextPayment());
            }
        }
    }

    /**
     * Calculates the Cost for the next Month or the next Year depending on the Mode
     */
    public void calculateCostMonthYear(){
        cost = 0;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        if(!isYearlyMode) {
            calendar.add(Calendar.MONTH, 1);
            long monthInFuture = calendar.getTimeInMillis();
            for (Subscription s : subscriptions

            ) {
                if (s.dayofnextPayment < monthInFuture) {
                    cost += s.price;
                }
            }
        }else {
            calendar.add(Calendar.YEAR, 1);
            long yearInFuture = calendar.getTimeInMillis();
            for (Subscription s : Objects.requireNonNull(subscriptions)

            ) {
                if (s.dayofnextPayment < yearInFuture)
                    if (s.frequency == 2) {
                        cost += s.price;
                    } else if (s.frequency == 1) {
                        cost += s.price*12;
                    } else {
                        cost += s.price;

                    }
            }
        }
    }

    /**
     * Changes the mode for Yearly/Monthly Calculation
     */
    public  void changeMode(){
        isYearlyMode = !isYearlyMode;
        calculateCostMonthYear();
    }

    /**
     * Returns if we are in the yearly  Mode
     * @return true = yearlyMode false=Monthly Mode
     */
    public boolean isYearlyMode() {
        return isYearlyMode;
    }

    /**
     * Returns the Calculated
     * @return Calculated Cost
     */
    public int getCostMonthYear() {
        return cost;
    }


    //endregion
    //regions Observers
    private class SubscriptionObserver implements Observer<List<Subscription>>{


        @Override
        public void onChanged(List<Subscription> data) {
            subscriptions = data;
            calculateCostMonthYear();

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
