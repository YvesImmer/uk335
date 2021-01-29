package ch.band.inf2019.uk335.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;

import ch.band.inf2019.uk335.db.DatabaseHandler;
import ch.band.inf2019.uk335.R;
import ch.band.inf2019.uk335.db.Subscription;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db;
    ScrollView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Subscription[] subs = db.fetch();
        for(Subscription sub: subs){
            createButton(sub);
        }

        /*

         */
    }

    void OnClick(){
    }
    
    private void createButton(Subscription sub){
        /*
        create button
        set button text to         
         */
    }
}