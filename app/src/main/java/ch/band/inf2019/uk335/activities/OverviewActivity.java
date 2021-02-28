package ch.band.inf2019.uk335.activities;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.viewmodel.MainViewModel;

public abstract class OverviewActivity  extends AppCompatActivity {
    private static final String TAG = "OverviewActivity";
    protected MainViewModel viewModel;

    protected void initSum() {
        RelativeLayout container = findViewById(R.id.relative_layout_frequency_container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Sum Field: clicked");
                viewModel.changeMode();
                setYearMontCost();
            }
        });
        setYearMontCost();

    }
    protected void setYearMontCost(){
        TextView textViewFrequency = findViewById(R.id.text_view_frequency_label);
        TextView textViewSum = findViewById(R.id.text_view_price_frequency);
        if (viewModel.isYearlyMode()) {
            textViewFrequency.setText(getString(R.string.yearly));
        } else {
            textViewFrequency.setText(getString(R.string.monthly));
        }
        int cost =viewModel.getCostMonthYear()/100;
        textViewSum.setText(NumberFormat.getCurrencyInstance().format(cost));
    }
}
