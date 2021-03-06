package ch.band.inf2019.uk335.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.viewmodel.CategoryAdapter;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import yuku.ambilwarna.AmbilWarnaDialog;

public class EditCategoryActivity extends AppCompatActivity {
    private static final String TAG = "EditCategoryActivity";

    TextInputEditText text_input_name;
    MainViewModel viewModel;
    Button btn_save;
    Button btn_delete;
    Button btn_color_picker;
    Categorie categorie;
    ArrayList<Categorie> categories;
    private boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        initViewModel();
        initCategory();
        initNameInput();
        initSaveButton();
        initDeleteButton();
        initColorPicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initColorPicker() {
        Log.d(TAG, "initColorPicker");
        btn_color_picker = findViewById(R.id.btn_color_picker);
        btn_color_picker.setBackgroundColor(categorie.color);
        btn_color_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
    }

    private void openColorPicker() {
        Log.d(TAG, "openColorPicker: called");

        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, categorie.color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                categorie.color = color;
                btn_color_picker.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getCategories().observe(this, new Observer<List<Categorie>>() {
                    @Override
                    public void onChanged(List<Categorie> categories) {
                        categories = viewModel.getCategories().getValue();
                    }
                });
    }

    private void initCategory() {
        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(CategoryAdapter.EXTRA_CATEGORIE_ID ,-1);
        if(categoryID >= 0){
            setTitle("Kategorie Bearbeiten");
            isNew = false;
            categorie = viewModel.getCategorieById(categoryID);
        }else {
            setTitle("Kategorie erstellen");
            isNew = true;
            categorie = new Categorie(getString(R.string.default_category_name), Color.GRAY);
        }
    }

    private void initDeleteButton() {
        btn_delete = findViewById(R.id.btn_delete_categorie);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmBox = AskOption();
                confirmBox.show();
            }
        });
    }

    private void initSaveButton() {
        btn_save = findViewById(R.id.btn_save_category);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNew){
                    viewModel.insert(categorie);
                }else {
                    viewModel.update(categorie);
                }
                gotoCategories();
            }
        });

    }

    private void initNameInput() {
        text_input_name = findViewById(R.id.text_input_category_name);
        text_input_name.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        categorie.title = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                }
        );
        text_input_name.setText(categorie.title);
    }

    private void gotoCategories(){
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
        finish();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Löschen")
                .setMessage("Wirklich löschen? Alle Abos dieser Kategorie werden auch gelöscht.")
                .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Deleting our category
                        viewModel.delete(categorie);
                        gotoCategories();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}