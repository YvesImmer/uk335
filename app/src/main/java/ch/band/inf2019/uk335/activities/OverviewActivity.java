package ch.band.inf2019.uk335.activities;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ch.band.inf2019.uk335.viewmodel.MainViewModel;

public abstract class OverviewActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private Button btn_goto_next_overview;
    private Button btn_new_item;

    @Override
    protected void onCreate(Bundle savedInstanceState){

    }

}
