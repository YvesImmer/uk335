package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.view.SubscriptionAdapter;
import ch.band.inf2019.uk335.viewModel.SubscriptionViewModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        SubscriptionAdapter adapter = new SubscriptionAdapter();
        recyclerView.setAdapter(adapter);

        SubscriptionViewModel subscriptionViewModel = ViewModelProviders.of(this).get(SubscriptionViewModel.class);
        subscriptionViewModel.getAllSubscriptions().observe(this, new Observer<List<Subscription>>() {
            @Override
            public void onChanged(List<Subscription> subscriptions) {
                adapter.setSubscriptions(subscriptions);
            }
        });
    }
}