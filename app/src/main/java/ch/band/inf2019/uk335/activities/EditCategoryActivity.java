package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.model.SubscriptionRepository;
import ch.band.inf2019.uk335.viewmodel.CategoryAdapter;
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
        text_input_name.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        categorie.title = s.toString();
                    }
                }
        );
        btn_save = findViewById(R.id.btn_save_category);
        btn_delete = findViewById(R.id.btn_delete_categorie);

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(CategoryAdapter.EXTRA_CATEGORIE_ID ,-1);
        if(categoryID >= 0){
            categorie = viewModel.getCategorieById(categoryID);
        }else {
            categorie = new Categorie("Test");
            viewModel.insert(categorie);

            categorie = viewModel.getLastCategory();
        }
        text_input_name.setText(categorie.title);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(categorie);
                gotoCategories();
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmBox = AskOption();
                confirmBox.show();
            }
        });
    }

    private void gotoCategories(){
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Löschen")
                .setMessage("Wirklich löschen? Alle Abos dieser Kategorie werden auch gelöscht.")

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        viewModel.delete(categorie);
                        gotoCategories();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}