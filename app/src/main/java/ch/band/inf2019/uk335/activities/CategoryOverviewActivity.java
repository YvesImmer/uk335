package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.viewmodel.CategoryAdapter;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;

/**
 * Presents the uses with an overview of their expenses by category
 * @author yvesimmer
 */
public class CategoryOverviewActivity extends AppCompatActivity {
    private static final String TAG = "CategoryOverview";

    private ArrayList<Categorie> categories;
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private Button btn_goto_subscriptions;
    private Button btn_new_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
        Log.d(TAG, "onCreate: started");
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Observer observer = new Observer<List<Categorie>>() {

            @Override
            public void onChanged(List<Categorie> categories) {
                categoryAdapter.setCategories((ArrayList<Categorie>) categories);
                categoryAdapter.notifyDataSetChanged();
            }
        };
        mainViewModel.getCategories().observe(this, observer);
        initRecyclerVView();
        initButtons();
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
        btn_goto_subscriptions.setOnClickListener((new View.OnClickListener(){
            @Override
            public  void onClick(View v){ gotoSubscriptions();};
        }));

        btn_new_category = findViewById(R.id.btn_new_category);
        btn_new_category.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                gotoNewCategory();
            }
        });
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