package ch.band.inf2019.uk335.db;


import android.graphics.Color;

import androidx.room.Entity;

import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorie_table")
public class Categorie {
    @PrimaryKey(autoGenerate = true) public int id;

    public String title;

    public int color;

    public Categorie(String title,int color) {
        this.title = title;
        this.color = color;
    }
    @Ignore
    public Categorie(String title) {
        this(title, Color.MAGENTA);
    }
//overridden so it is displayed in the category select spinner

    @Override
    public String toString(){
        return title;
    }
}
