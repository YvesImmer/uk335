package ch.band.inf2019.uk335.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubscriptionDao {

    @Insert
    void insert(Subscription... subscription);

    @Update
    void updateSubscriptions(Subscription... subscription);

    @Delete
    void deleteSubscriptions(Subscription... subscription);

    @Query("SELECT * FROM subscription_table ORDER BY dayofnextPayment DESC")
    LiveData<List<Subscription>> getAllSubscriptions();

    /*@Query("SELECT * FROM subscription_table WHERE categorieid=:categorieid")
    LiveData<List<Subscription>> findSubscriptionsForCategorie(final long categorieid);*/
}
