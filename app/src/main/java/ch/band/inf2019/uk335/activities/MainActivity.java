package ch.band.inf2019.uk335.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    private SubscriptionAdapter adapter;
    private Button btn_goto_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setTitle("Abos");




        mainViewModel.getSubscriptions().observe( this, subscriptions -> {
            // RELEASE
            adapter.setSubscriptions((ArrayList<Subscription>) subscriptions);
            adapter.notifyDataSetChanged();
        });
        mainViewModel.getSubscriptions().observe(this, subscriptions -> {
            setYearMontCost();
                }
        );
        mainViewModel.getCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                adapter.setCategories((ArrayList<Categorie>) categories);
                adapter.notifyDataSetChanged();
            }
        });
        mainViewModel.updateAllDayOfNextPayment();
        initRecyclerView();
        initButtons();
        initSum();
    }

    private void initSum() {
        RelativeLayout container = findViewById(R.id.relative_layout_frequency_container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.changeMode();
            }
        });
        setYearMontCost();

    }
    private void setYearMontCost(){
        TextView textViewFrequency = findViewById(R.id.text_view_frequency_label);
        TextView textViewSum = findViewById(R.id.text_view_price_frequency);
        if (mainViewModel.isYearlyMode()) {
            textViewFrequency.setText(getString(R.string.yearly));
        } else {
            textViewFrequency.setText(getString(R.string.monthly));
        }
        textViewSum.setText(String.valueOf(mainViewModel.getCostMonthYear()));
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