package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<Subscription>  subscriptions;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    SubscriptionAdapter adapter;
    private Button btn_goto_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setTitle("Abos");

        Observer observer = new Observer<List<Subscription>>() {
            @Override
            public void onChanged(List<Subscription> subscriptions) {
                // RELEASE
                adapter.setSubscriptions((ArrayList<ch.band.inf2019.uk335.db.Subscription>) subscriptions);
                adapter.notifyDataSetChanged();
            }
        };
        
        Observer cObserver = new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                adapter.setCategories((ArrayList<Categorie>) categories);
                adapter.notifyDataSetChanged();
            }
        };
        mainViewModel.getSubscriptions().observe( this,observer);
        mainViewModel.getCategories().observe(this, cObserver);
        mainViewModel.updateAllDayOfNextPayment();
        initRecyclerView();
        initButtons();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecylerView: called");
        recyclerView = findViewById(R.id.recycler_view_subscriptions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new SubscriptionAdapter();
        recyclerView.setAdapter(adapter);

    }

    private void initButtons(){
        btn_goto_categories = findViewById(R.id.btn_goto_categories);
        btn_goto_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCategories();
            }
        });

        Button btn_new_subscription = findViewById(R.id.btn_new_subscription);
        btn_new_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewSubscritpion();
            }
        });

    }

    private void gotoCategories(){
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }

    private void gotoNewSubscritpion(){
        Intent intent = new Intent(this, EditSubscritpionActivity.class);
        startActivity(intent);
    }

}