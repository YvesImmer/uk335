package ch.band.inf2019.uk335.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class EditSubscritpionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "EditSubscritpionActivit";

    private Subscription subscription;
    private ArrayList<Categorie> categories;
    private MainViewModel viewModel;
    private TextInputEditText nameTextInput;
    private Spinner categorySpinner;
    private Spinner frequencySpinner;
    private TextInputEditText priceTextInput;
    private Button btn_save;
    private Button btn_delete;
    TextView tv_selectedDate;
    String dueDate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setContentView(R.layout.activity_edit_subscription);
        viewModel.getCategories().observe(this, new Observer<List<Categorie>>() {
            @Override
            public void onChanged(List<Categorie> categories) {
                categories = viewModel.getCategories().getValue();
            }
        });
        Intent intent = getIntent();
        int subscriptionID = intent.getIntExtra(SubscriptionAdapter.EXTRA_SUBSCRIPTION_ID,-1);
        if(subscriptionID >= 0){
            subscription = viewModel.getSubscriptionById(subscriptionID);
            setTitle("Abo berabeiten");
        }
        else{
            setTitle("Abo erstellen");
            viewModel.insert(new Subscription(viewModel.getFirstCategoryID()));
            subscription = viewModel.getLastSubscription();
            Calendar c = Calendar.getInstance();
            subscription.dayofnextPayment=c.getTime().getTime();
            subscription.title = "Neues Abo";
            subscription.frequency = 1;
            subscription.price = 0;
        }
        setupInputs();
    }

    private void setupInputs() {
        initDateSelection();
        initCategorySpinner();
        initSaveButton();
        initDeleteButton();
        initTitleInput();
        initPriceInput();
    }

    private void initPriceInput() {
        priceTextInput = findViewById(R.id.text_input_price);
        priceTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceTextInput.setText(String.valueOf(subscription.price/100));
        priceTextInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        subscription.price = (int)(Double.parseDouble(s.toString())*100);
                    }
                }
        );
    }

    private void initTitleInput() {
        nameTextInput = findViewById(R.id.text_input_name);
        nameTextInput.setText(subscription.title);
        nameTextInput.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        subscription.title = s.toString();
                    }
                }
        );
    }

    private void initDeleteButton() {
        btn_delete = findViewById(R.id.btn_delete_subscription);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog confirmBox = AskOption();
                confirmBox.show();
            }
        });
    }

    private void initSaveButton() {
        btn_save = findViewById(R.id.btn_save_subscription);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.update(subscription);
                gotoSubscriptions();
            }
        });
    }

    private void initCategorySpinner() {
        categorySpinner = findViewById(R.id.spinner_category_select);
        ArrayAdapter<Categorie> categorySpinnerAdapter = new ArrayAdapter<Categorie>(this,
                android.R.layout.simple_spinner_item, viewModel.getCategories().getValue());
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(this);
    }

    private void initDateSelection() {
        tv_selectedDate = findViewById(R.id.text_view_picked_date);
        dueDate = DateFormat.getDateInstance().format(subscription.dayofnextPayment);
        tv_selectedDate.setText(dueDate);
        Button btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void gotoSubscriptions() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        subscription.dayofnextPayment = c.getTimeInMillis();
        TextView tv_selectedDate = findViewById(R.id.text_view_picked_date);
        tv_selectedDate.setText(currentDateString);
    }


    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Löschen")
                .setMessage("Wirklich löschen?")
                .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        viewModel.delete(subscription);
                        gotoSubscriptions();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Categorie selected = (Categorie)categorySpinner.getSelectedItem();
        Log.d(TAG, "onItemSelected called selected category: " + selected.title);
        subscription.categorieid = selected.id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}