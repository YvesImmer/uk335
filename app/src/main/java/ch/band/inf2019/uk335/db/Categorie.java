package ch.band.inf2019.uk335.db;

import androidx.room.Entity;

import androidx.room.PrimaryKey;

@Entity(tableName = "categorie_table")
public class Categorie {
    @PrimaryKey(autoGenerate = true) public int id;

    public String title;

    public Categorie(String title) {
        this.title = title;
    }
}
