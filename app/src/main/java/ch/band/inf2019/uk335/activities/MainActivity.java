package ch.band.inf2019.uk335.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

/**
 * Launch Activity, Displays all existing Subscriptions
 */
public class MainActivity extends OverviewActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<Subscription>  subscriptions;
    private RecyclerView recyclerView;
    private SubscriptionAdapter adapter;
    private Button btn_goto_categories;

    /**
     * Initializes the view, does most of the hard work
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setTitle("Abos");

        viewModel.getSubscriptions().observe( this, subscriptions -> {
            // RELEASE
            adapter.setSubscriptions((ArrayList<Subscription>) subscriptions);
            adapter.notifyDataSetChanged();
        });
        viewModel.getSubscriptions().observe(this, subscriptions -> {
            setYearMontCost();
                }
        );
        viewModel.getCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                adapter.setCategories((ArrayList<Categorie>) categories);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.updateAllDayOfNextPayment();
        initRecyclerView();
        initButtons();
        initSum();
    }

    /**
     * Initializes the RecyclerView
     */
    private void initRecyclerView(){
    Log.d(TAG, "initRecylerView: called");
    recyclerView = findViewById(R.id.recycler_view_subscriptions);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    adapter = new SubscriptionAdapter();
    recyclerView.setAdapter(adapter);

    }

    /**
     * Initializes the navigation Buttons
     */
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

    /**
     * Opens the CategoryOverview
     */
    private void gotoCategories(){
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }

    /**
     * Opens a new EditSubscriptionActivity with a new Subscription
     */
    private void gotoNewSubscritpion(){
        Intent intent = new Intent(this, EditSubscritpionActivity.class);
        startActivity(intent);
    }

}