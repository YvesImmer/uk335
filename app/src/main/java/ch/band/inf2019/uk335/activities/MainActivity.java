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
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.MainActivityViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    //vars
    private ArrayList<Subscription>  subscriptions;
    private MainActivityViewModel mainActivityViewModel;
    private RecyclerView recyclerView;
    SubscriptionAdapter adapter;
    private Object Subscription;
    private Button btn_goto_categories;
    private Button btn_new_subscription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        initData();
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        Observer observer = new Observer<List<Subscription>>() {
            @Override
            public void onChanged(List<Subscription> subscriptions) {
                    adapter.notifyDataSetChanged();
            }
        };
        mainActivityViewModel.getSubscriptions().observe( this,observer);

        initButtons();
    }

    private void initData(){
        //TODO get Categories and Subscriptions from Repository

        initRecyclerView();
    }

    //TODO set and update price in bottom

    private void initRecyclerView(){
        Log.d(TAG, "initRecylerView: called");
        recyclerView = findViewById(R.id.recycler_view_subscriptions);
        adapter = new SubscriptionAdapter(this, mainActivityViewModel.getSubscriptions().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initButtons(){
        btn_goto_categories = findViewById(R.id.btn_goto_categories);
        btn_goto_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCategories();
            }
        });

        btn_new_subscription = findViewById(R.id.btn_new_subscription);
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