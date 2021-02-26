package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.model.SubscriptionRepository;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class EditCategoryActivity extends AppCompatActivity {
    TextInputEditText text_input_name;
    MainViewModel viewModel;
    Button btn_save;
    Button btn_delete;
    Categorie categorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setContentView(R.layout.activity_edit_category);
        text_input_name = findViewById(R.id.text_input_category_name);
        btn_save = findViewById(R.id.btn_save_category);

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(SubscriptionAdapter.EXTRA_SUBSCRIPTION_ID,-1);

        if(categoryID >= 0){
            categorie = viewModel.getCategorieById(categoryID);
        }else {
            categorie = new Categorie("Neue Kategorie");
            viewModel.insert(categorie);
        }
        text_input_name.setText(categorie.title);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(categorie);
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.delete(categorie);
            }
        });


    }
}