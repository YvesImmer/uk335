package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.band.inf2019.uk335.R;

/**
 * Presents the uses with an overview of their expenses by category
 * @author yvesimmer
 */
public class CategoryOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_overview);
    }
}