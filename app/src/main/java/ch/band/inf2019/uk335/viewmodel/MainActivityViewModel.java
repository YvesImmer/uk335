package ch.band.inf2019.uk335.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.model.NewThreadExecutor;
import ch.band.inf2019.uk335.model.SubscriptionRepository;

public class MainActivityViewModel extends ViewModel {
    //TODO impement view Model
    private MutableLiveData<List<Subscription>> mSubscriptions;
    SubscriptionRepository repository;


    public LiveData<List<Subscription>> getSubscriptions(){
        return mSubscriptions;
    }


}
