package ch.band.inf2019.uk335.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

public class EditSubscritpionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Subscription subscription;
    private ArrayList<Categorie> categories;
    private MainViewModel viewModel;
    private TextInputEditText nameTextInput;
    private Spinner categorySpinner;
    private Spinner frequencySpinner;
    private TextInputEditText priceTextInput;
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
        }
        else{
            viewModel.insert(new Subscription(viewModel.getFirstCategoryID()));
            subscription = viewModel.getLastSubscription();
            Calendar c = Calendar.getInstance();
            dueDate = DateFormat.getDateInstance().format(c.getTime());
        }
        setupInputs();

    }

    private void setupInputs() {
        tv_selectedDate = findViewById(R.id.text_view_picked_date);
        tv_selectedDate.setText(dueDate);
        //Category Spinner
        categorySpinner = findViewById(R.id.spinner_category_select);
        ArrayAdapter<Categorie> categorySpinnerAdapter = new ArrayAdapter<Categorie>(this,
                android.R.layout.simple_spinner_item, categories);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        Button btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        nameTextInput = findViewById(R.id.text_input_name);
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

        priceTextInput = findViewById(R.id.text_input_price);
        priceTextInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                        subscription.price = (int) Double.parseDouble(s.toString())*100;
                    }
                }
        );
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
}