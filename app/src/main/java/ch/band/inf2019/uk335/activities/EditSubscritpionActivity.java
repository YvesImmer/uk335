package ch.band.inf2019.uk335.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Categorie;
import ch.band.inf2019.uk335.db.Subscription;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;
import ch.band.inf2019.uk335.viewmodel.SubscriptionAdapter;

public class EditSubscritpionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Subscription subscription;
    private MainViewModel viewModel;
    private TextInputEditText nameTextInput;
    private Spinner categorySpinner;
    private TextInputEditText priceTextInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        setContentView(R.layout.activity_edit_subscription);

        categorySpinner = findViewById(R.id.spinner_category_select);
        ArrayAdapter<Categorie> spinnerAdapter = new ArrayAdapter<Categorie>(this,
                R.layout.support_simple_spinner_dropdown_item, viewModel.getCategories().getValue());
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Button btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Intent intent = getIntent();
        int subscriptionID = intent.getIntExtra(SubscriptionAdapter.EXTRA_SUBSCRIPTION_ID,-1);
        if(subscriptionID == -1){
            viewModel.insert(new Subscription(viewModel.getFirstCategoryID()));
            viewModel.getSubscriptionById(subscriptionID);
        }
        else{
            Calendar c = Calendar.getInstance();
            String currentDateString = DateFormat.getDateInstance().format(c.getTime());
            TextView tv_selectedDate = findViewById(R.id.text_view_picked_date);
            tv_selectedDate.setText(currentDateString);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        TextView tv_selectedDate = findViewById(R.id.text_view_picked_date);
        tv_selectedDate.setText(currentDateString);
    }
}