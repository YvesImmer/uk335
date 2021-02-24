package ch.band.inf2019.uk335.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.model.NewThreadExecutor;
import ch.band.inf2019.uk335.model.SubscriptionRepository;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Subscription>> subscriptions;
    private LiveData<List<Categorie>> categories;
    SubscriptionRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = SubscriptionRepository.getInstance(application);
        subscriptions = repository.getAllSubscriptions();
        categories = repository.getAllCategories();
    }

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



}
