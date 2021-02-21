package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    //vars
    private ArrayList<Subscription> subscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        initData();

    }


    private void initData(){
        //TODO get Categories and Subscriptions from Repository

        initRecyclerView();
    }

    //TODO set and update price in bottom

    private void initRecyclerView(){
        Log.d(TAG, "initRecylerView: called");
        RecyclerView recyclerView = findViewById(R.id.recycler_view_subscriptions);
        SubscriptionAdapter adapter = new SubscriptionAdapter(this, subscriptions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_subscription);
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.hasFixedSize();
//
//        SubscriptionAdapter adapter = new SubscriptionAdapter();
//        recyclerView.setAdapter(adapter);
//
//        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
//        ViewModel.getAllSubscriptions().observe(this, new Observer<List<Subscription>>() {
//            @Override
//            public void onChanged(List<Subscription> subscriptions) {
//                adapter.setSubscriptions(subscriptions);
//            }
//        });
//    }
}