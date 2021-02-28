package ch.band.inf2019.uk335.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.CategoryAdapter;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;

/**
 * Presents the uses with an overview of their expenses by category
 * @author yvesimmer
 */
public class CategoryOverviewActivity extends OverviewActivity {
    private static final String TAG = "CategoryOverview";
    private ArrayList<Categorie> categories;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private Button btn_goto_subscriptions;
    private Button btn_new_category;
    private TextView textViewSum;
    TextView textViewFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
        setTitle("Kategorien");
        Log.d(TAG, "onCreate: started");
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        viewModel.getCategories().observe(this,
                categories -> {
            categoryAdapter.setCategories((ArrayList<Categorie>) categories);
            categoryAdapter.notifyDataSetChanged();
        });
        viewModel.getSubscriptions().observe(this, subscriptions -> {
            categoryAdapter.setSubscriptions((ArrayList<Subscription>) subscriptions);
            categoryAdapter.notifyDataSetChanged();
        }
        );

        initRecyclerVView();
        initButtons();
        initSum();
    }

    private void initRecyclerVView(){
        Log.d(TAG, "initRecyclerView: called");
        recyclerView = findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);
    }

    private void initButtons(){
        btn_goto_subscriptions = findViewById(R.id.btn_goto_subscriptions);
        btn_goto_subscriptions.setOnClickListener((v -> gotoSubscriptions()));

        btn_new_category = findViewById(R.id.btn_new_category);
        btn_new_category.setOnClickListener(v -> gotoNewCategory());
    }
    private void gotoSubscriptions(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void gotoNewCategory(){
        Intent intent = new Intent (this, EditCategoryActivity.class);
        startActivity(intent);
    }
}